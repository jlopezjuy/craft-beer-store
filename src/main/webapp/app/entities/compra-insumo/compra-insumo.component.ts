import { Component, OnInit, OnDestroy, ChangeDetectorRef } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { ICompraInsumo } from 'app/shared/model/compra-insumo.model';
import { AccountService } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { CompraInsumoService } from './compra-insumo.service';
import { SidebarService } from '../../services/sidebar.service';
import { EChartOption } from 'echarts';
import { LocalStorageService } from 'ngx-webstorage';
import { IEmpresa } from '../../shared/model/empresa.model';
import { MatTableDataSource, PageEvent } from '@angular/material';

@Component({
  selector: 'jhi-compra-insumo',
  templateUrl: './compra-insumo.component.html'
})
export class CompraInsumoComponent implements OnInit, OnDestroy {
  currentAccount: any;
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
  dataSource: any;
  displayedColumns: string[] = [
    'nroFactura',
    'fecha',
    'subtotal',
    'gastoDeEnvio',
    'impuesto',
    'total',
    'proveedor',
    'estadoCompra',
    'actions'
  ];
  pageEvent: PageEvent;
  public sidebarVisible = true;
  public visitorsOptions: EChartOption = {};
  public visitsOptions: EChartOption = {};

  constructor(
    protected compraInsumoService: CompraInsumoService,
    protected parseLinks: JhiParseLinks,
    protected jhiAlertService: JhiAlertService,
    protected accountService: AccountService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    private $localStorage: LocalStorageService,
    private sidebarService: SidebarService,
    private cdr: ChangeDetectorRef
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
    const empresa: IEmpresa = this.$localStorage.retrieve('empresa');
    this.compraInsumoService
      .findAllByEmpresa(
        {
          page: this.page - 1,
          size: this.itemsPerPage,
          sort: this.sort()
        },
        empresa.id
      )
      .subscribe(
        (res: HttpResponse<ICompraInsumo[]>) => this.paginateCompraInsumos(res.body, res.headers),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
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
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInCompraInsumos();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ICompraInsumo) {
    return item.id;
  }

  registerChangeInCompraInsumos() {
    this.eventSubscriber = this.eventManager.subscribe('compraInsumoListModification', response => this.loadAll());
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
    this.dataSource = new MatTableDataSource<ICompraInsumo>(this.compraInsumos);
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  onPaginateChange(event: PageEvent) {
    this.page = event.pageIndex + 1;
    this.loadPage(event.pageIndex + 1);
  }

  toggleFullWidth() {
    this.sidebarService.toggle();
    this.sidebarVisible = this.sidebarService.getStatus();
    this.cdr.detectChanges();
  }
}
