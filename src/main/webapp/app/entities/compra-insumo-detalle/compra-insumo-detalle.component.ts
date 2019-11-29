import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICompraInsumoDetalle } from 'app/shared/model/compra-insumo-detalle.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { CompraInsumoDetalleService } from './compra-insumo-detalle.service';
import { CompraInsumoDetalleDeleteDialogComponent } from './compra-insumo-detalle-delete-dialog.component';

@Component({
  selector: 'jhi-compra-insumo-detalle',
  templateUrl: './compra-insumo-detalle.component.html'
})
export class CompraInsumoDetalleComponent implements OnInit, OnDestroy {
  compraInsumoDetalles: ICompraInsumoDetalle[];
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
    protected compraInsumoDetalleService: CompraInsumoDetalleService,
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
    this.compraInsumoDetalleService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<ICompraInsumoDetalle[]>) => this.paginateCompraInsumoDetalles(res.body, res.headers));
  }

  loadPage(page: number) {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  transition() {
    this.router.navigate(['/compra-insumo-detalle'], {
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
      '/compra-insumo-detalle',
      {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInCompraInsumoDetalles();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ICompraInsumoDetalle) {
    return item.id;
  }

  registerChangeInCompraInsumoDetalles() {
    this.eventSubscriber = this.eventManager.subscribe('compraInsumoDetalleListModification', () => this.loadAll());
  }

  delete(compraInsumoDetalle: ICompraInsumoDetalle) {
    const modalRef = this.modalService.open(CompraInsumoDetalleDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.compraInsumoDetalle = compraInsumoDetalle;
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateCompraInsumoDetalles(data: ICompraInsumoDetalle[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.compraInsumoDetalles = data;
  }
}
