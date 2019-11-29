import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IMovimientoBarril } from 'app/shared/model/movimiento-barril.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { MovimientoBarrilService } from './movimiento-barril.service';
import { MovimientoBarrilDeleteDialogComponent } from './movimiento-barril-delete-dialog.component';

@Component({
  selector: 'jhi-movimiento-barril',
  templateUrl: './movimiento-barril.component.html'
})
export class MovimientoBarrilComponent implements OnInit, OnDestroy {
  movimientoBarrils: IMovimientoBarril[];
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
    protected movimientoBarrilService: MovimientoBarrilService,
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
    this.movimientoBarrilService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IMovimientoBarril[]>) => this.paginateMovimientoBarrils(res.body, res.headers));
  }

  loadPage(page: number) {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  transition() {
    this.router.navigate(['/movimiento-barril'], {
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
      '/movimiento-barril',
      {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInMovimientoBarrils();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IMovimientoBarril) {
    return item.id;
  }

  registerChangeInMovimientoBarrils() {
    this.eventSubscriber = this.eventManager.subscribe('movimientoBarrilListModification', () => this.loadAll());
  }

  delete(movimientoBarril: IMovimientoBarril) {
    const modalRef = this.modalService.open(MovimientoBarrilDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.movimientoBarril = movimientoBarril;
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateMovimientoBarrils(data: IMovimientoBarril[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.movimientoBarrils = data;
  }
}
