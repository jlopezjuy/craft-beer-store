import { Component, OnInit, OnDestroy, ChangeDetectorRef } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { IPuntoDeVenta } from 'app/shared/model/punto-de-venta.model';
import { AccountService } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { PuntoDeVentaService } from './punto-de-venta.service';
import { LocalStorageService } from 'ngx-webstorage';
import { ICliente } from 'app/shared/model/cliente.model';
import { SidebarService } from '../../services/sidebar.service';
import { EChartOption } from 'echarts';
import { MatTableDataSource, PageEvent } from '@angular/material';

@Component({
  selector: 'jhi-punto-de-venta',
  templateUrl: './punto-de-venta.component.html'
})
export class PuntoDeVentaComponent implements OnInit, OnDestroy {
  currentAccount: any;
  puntoDeVentas: IPuntoDeVenta[];
  error: any;
  success: any;
  eventSubscriber: Subscription;
  currentSearch: string;
  routeData: any;
  links: any;
  totalItems: any;
  itemsPerPage: any;
  page: any;
  predicate: any;
  previousPage: any;
  reverse: any;
  cliente: ICliente;
  public sidebarVisible = true;
  public visitorsOptions: EChartOption = {};
  public visitsOptions: EChartOption = {};
  dataSource: any;
  displayedColumns: string[] = ['nombre', 'direccionDeEntrega', 'localidad', 'notas', 'actions'];
  pageEvent: PageEvent;

  constructor(
    protected puntoDeVentaService: PuntoDeVentaService,
    protected parseLinks: JhiParseLinks,
    protected jhiAlertService: JhiAlertService,
    protected accountService: AccountService,
    protected activatedRoute: ActivatedRoute,
    protected dataUtils: JhiDataUtils,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected $localStorage: LocalStorageService,
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
    this.currentSearch =
      this.activatedRoute.snapshot && this.activatedRoute.snapshot.params['search'] ? this.activatedRoute.snapshot.params['search'] : '';
  }

  loadAll() {
    this.cliente = this.$localStorage.retrieve('cliente');
    if (this.currentSearch) {
      this.puntoDeVentaService
        .search({
          page: this.page - 1,
          query: this.currentSearch,
          size: this.itemsPerPage,
          sort: this.sort()
        })
        .subscribe(
          (res: HttpResponse<IPuntoDeVenta[]>) => this.paginatePuntoDeVentas(res.body, res.headers),
          (res: HttpErrorResponse) => this.onError(res.message)
        );
      return;
    }
    this.puntoDeVentaService
      .queryByCliente(
        {
          page: this.page - 1,
          size: this.itemsPerPage,
          sort: this.sort()
        },
        this.cliente.id
      )
      .subscribe(
        (res: HttpResponse<IPuntoDeVenta[]>) => this.paginatePuntoDeVentas(res.body, res.headers),
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
    this.router.navigate(['/punto-de-venta'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        search: this.currentSearch,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    });
    this.loadAll();
  }

  clear() {
    this.page = 0;
    this.currentSearch = '';
    this.router.navigate([
      '/punto-de-venta',
      {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

  search(query) {
    if (!query) {
      return this.clear();
    }
    this.page = 0;
    this.currentSearch = query;
    this.router.navigate([
      '/punto-de-venta',
      {
        search: this.currentSearch,
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
    this.eventSubscriber = this.eventManager.subscribe('puntoDeVentaListModification', response => this.loadAll());
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
    this.dataSource = new MatTableDataSource<IPuntoDeVenta>(this.puntoDeVentas);
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  previousState() {
    this.$localStorage.clear('cliente');
    this.router.navigate(['/admin/admin/cliente']);
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
