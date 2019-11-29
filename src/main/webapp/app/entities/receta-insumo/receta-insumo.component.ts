import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IRecetaInsumo } from 'app/shared/model/receta-insumo.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { RecetaInsumoService } from './receta-insumo.service';
import { RecetaInsumoDeleteDialogComponent } from './receta-insumo-delete-dialog.component';

@Component({
  selector: 'jhi-receta-insumo',
  templateUrl: './receta-insumo.component.html'
})
export class RecetaInsumoComponent implements OnInit, OnDestroy {
  recetaInsumos: IRecetaInsumo[];
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
    protected recetaInsumoService: RecetaInsumoService,
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
    this.recetaInsumoService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IRecetaInsumo[]>) => this.paginateRecetaInsumos(res.body, res.headers));
  }

  loadPage(page: number) {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  transition() {
    this.router.navigate(['/receta-insumo'], {
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
      '/receta-insumo',
      {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInRecetaInsumos();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IRecetaInsumo) {
    return item.id;
  }

  registerChangeInRecetaInsumos() {
    this.eventSubscriber = this.eventManager.subscribe('recetaInsumoListModification', () => this.loadAll());
  }

  delete(recetaInsumo: IRecetaInsumo) {
    const modalRef = this.modalService.open(RecetaInsumoDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.recetaInsumo = recetaInsumo;
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateRecetaInsumos(data: IRecetaInsumo[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.recetaInsumos = data;
  }
}
