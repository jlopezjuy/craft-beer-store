import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IInsumoRecomendado } from 'app/shared/model/insumo-recomendado.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { InsumoRecomendadoService } from './insumo-recomendado.service';
import { InsumoRecomendadoDeleteDialogComponent } from './insumo-recomendado-delete-dialog.component';

@Component({
  selector: 'jhi-insumo-recomendado',
  templateUrl: './insumo-recomendado.component.html'
})
export class InsumoRecomendadoComponent implements OnInit, OnDestroy {
  insumoRecomendados: IInsumoRecomendado[];
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
    protected insumoRecomendadoService: InsumoRecomendadoService,
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
    this.insumoRecomendadoService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IInsumoRecomendado[]>) => this.paginateInsumoRecomendados(res.body, res.headers));
  }

  loadPage(page: number) {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  transition() {
    this.router.navigate(['/insumo-recomendado'], {
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
      '/insumo-recomendado',
      {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInInsumoRecomendados();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IInsumoRecomendado) {
    return item.id;
  }

  registerChangeInInsumoRecomendados() {
    this.eventSubscriber = this.eventManager.subscribe('insumoRecomendadoListModification', () => this.loadAll());
  }

  delete(insumoRecomendado: IInsumoRecomendado) {
    const modalRef = this.modalService.open(InsumoRecomendadoDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.insumoRecomendado = insumoRecomendado;
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateInsumoRecomendados(data: IInsumoRecomendado[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.insumoRecomendados = data;
  }
}
