import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICompraInsumo } from 'app/shared/model/compra-insumo.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { CompraInsumoService } from './compra-insumo.service';
import { CompraInsumoDeleteDialogComponent } from './compra-insumo-delete-dialog.component';

@Component({
  selector: 'jhi-compra-insumo',
  templateUrl: './compra-insumo.component.html'
})
export class CompraInsumoComponent implements OnInit, OnDestroy {
  compraInsumos: ICompraInsumo[];
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
    protected compraInsumoService: CompraInsumoService,
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
    this.compraInsumoService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<ICompraInsumo[]>) => this.paginateCompraInsumos(res.body, res.headers));
  }

  loadPage(page: number) {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  transition() {
    this.router.navigate(['/compra-insumo'], {
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
      '/compra-insumo',
      {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInCompraInsumos();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ICompraInsumo) {
    return item.id;
  }

  registerChangeInCompraInsumos() {
    this.eventSubscriber = this.eventManager.subscribe('compraInsumoListModification', () => this.loadAll());
  }

  delete(compraInsumo: ICompraInsumo) {
    const modalRef = this.modalService.open(CompraInsumoDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.compraInsumo = compraInsumo;
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateCompraInsumos(data: ICompraInsumo[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.compraInsumos = data;
  }
}
