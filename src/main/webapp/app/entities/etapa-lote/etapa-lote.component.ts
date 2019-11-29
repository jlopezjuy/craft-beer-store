import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IEtapaLote } from 'app/shared/model/etapa-lote.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { EtapaLoteService } from './etapa-lote.service';
import { EtapaLoteDeleteDialogComponent } from './etapa-lote-delete-dialog.component';

@Component({
  selector: 'jhi-etapa-lote',
  templateUrl: './etapa-lote.component.html'
})
export class EtapaLoteComponent implements OnInit, OnDestroy {
  etapaLotes: IEtapaLote[];
  error: any;
  success: any;
  eventSubscriber: Subscription;
  routeData: any;
  links: any;
  totalItems: any;
  itemsPerPage: any;
  page: any;
  predicate: any;
  previousPage: any;
  reverse: any;

  constructor(
    protected etapaLoteService: EtapaLoteService,
    protected parseLinks: JhiParseLinks,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.routeData = this.activatedRoute.data.subscribe(data => {
      this.page = data.pagingParams.page;
      this.previousPage = data.pagingParams.page;
      this.reverse = data.pagingParams.ascending;
      this.predicate = data.pagingParams.predicate;
    });
  }

  loadAll() {
    this.etapaLoteService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IEtapaLote[]>) => this.paginateEtapaLotes(res.body, res.headers));
  }

  loadPage(page: number) {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  transition() {
    this.router.navigate(['/etapa-lote'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    });
    this.loadAll();
  }

  clear() {
    this.page = 0;
    this.router.navigate([
      '/etapa-lote',
      {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInEtapaLotes();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IEtapaLote) {
    return item.id;
  }

  registerChangeInEtapaLotes() {
    this.eventSubscriber = this.eventManager.subscribe('etapaLoteListModification', () => this.loadAll());
  }

  delete(etapaLote: IEtapaLote) {
    const modalRef = this.modalService.open(EtapaLoteDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.etapaLote = etapaLote;
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateEtapaLotes(data: IEtapaLote[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.etapaLotes = data;
  }
}
