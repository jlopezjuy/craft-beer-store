import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IMedicionLote } from 'app/shared/model/medicion-lote.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { MedicionLoteService } from './medicion-lote.service';
import { MedicionLoteDeleteDialogComponent } from './medicion-lote-delete-dialog.component';

@Component({
  selector: 'jhi-medicion-lote',
  templateUrl: './medicion-lote.component.html'
})
export class MedicionLoteComponent implements OnInit, OnDestroy {
  medicionLotes: IMedicionLote[];
  eventSubscriber: Subscription;
  itemsPerPage: number;
  links: any;
  page: any;
  predicate: any;
  reverse: any;
  totalItems: number;

  constructor(
    protected medicionLoteService: MedicionLoteService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.medicionLotes = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.reverse = true;
  }

  loadAll() {
    this.medicionLoteService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IMedicionLote[]>) => this.paginateMedicionLotes(res.body, res.headers));
  }

  reset() {
    this.page = 0;
    this.medicionLotes = [];
    this.loadAll();
  }

  loadPage(page) {
    this.page = page;
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInMedicionLotes();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IMedicionLote) {
    return item.id;
  }

  registerChangeInMedicionLotes() {
    this.eventSubscriber = this.eventManager.subscribe('medicionLoteListModification', () => this.reset());
  }

  delete(medicionLote: IMedicionLote) {
    const modalRef = this.modalService.open(MedicionLoteDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.medicionLote = medicionLote;
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateMedicionLotes(data: IMedicionLote[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    for (let i = 0; i < data.length; i++) {
      this.medicionLotes.push(data[i]);
    }
  }
}
