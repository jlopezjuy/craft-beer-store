import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPuntoDeVenta } from 'app/shared/model/punto-de-venta.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { PuntoDeVentaService } from './punto-de-venta.service';
import { PuntoDeVentaDeleteDialogComponent } from './punto-de-venta-delete-dialog.component';

@Component({
  selector: 'jhi-punto-de-venta',
  templateUrl: './punto-de-venta.component.html'
})
export class PuntoDeVentaComponent implements OnInit, OnDestroy {
  puntoDeVentas: IPuntoDeVenta[];
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
    protected puntoDeVentaService: PuntoDeVentaService,
    protected parseLinks: JhiParseLinks,
    protected activatedRoute: ActivatedRoute,
    protected dataUtils: JhiDataUtils,
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
    this.puntoDeVentaService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IPuntoDeVenta[]>) => this.paginatePuntoDeVentas(res.body, res.headers));
  }

  loadPage(page: number) {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  transition() {
    this.router.navigate(['/punto-de-venta'], {
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
      '/punto-de-venta',
      {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInPuntoDeVentas();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPuntoDeVenta) {
    return item.id;
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  registerChangeInPuntoDeVentas() {
    this.eventSubscriber = this.eventManager.subscribe('puntoDeVentaListModification', () => this.loadAll());
  }

  delete(puntoDeVenta: IPuntoDeVenta) {
    const modalRef = this.modalService.open(PuntoDeVentaDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.puntoDeVenta = puntoDeVenta;
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginatePuntoDeVentas(data: IPuntoDeVenta[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.puntoDeVentas = data;
  }
}
