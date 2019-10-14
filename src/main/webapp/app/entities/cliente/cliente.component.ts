import { Component, OnInit, OnDestroy, ChangeDetectorRef } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { ICliente } from 'app/shared/model/cliente.model';
import { AccountService } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { ClienteService } from './cliente.service';
import { LocalStorageService } from 'ngx-webstorage';
import { IEmpresa } from 'app/shared/model/empresa.model';
import { MatTableDataSource, PageEvent } from '@angular/material';
import { SidebarService } from '../../services/sidebar.service';
import { EChartOption } from 'echarts';

@Component({
  selector: 'jhi-cliente',
  templateUrl: './cliente.component.html'
})
export class ClienteComponent implements OnInit, OnDestroy {
  currentAccount: any;
  clientes: ICliente[];
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
  dataSource: any;
  displayedColumns: string[] = ['nombreApellido', 'domicilio', 'tipoCliente', 'telefono', 'correo', 'actions'];
  pageEvent: PageEvent;
  public sidebarVisible = true;
  public visitorsOptions: EChartOption = {};
  public visitsOptions: EChartOption = {};

  constructor(
    protected clienteService: ClienteService,
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
    this.currentSearch =
      this.activatedRoute.snapshot && this.activatedRoute.snapshot.params['search'] ? this.activatedRoute.snapshot.params['search'] : '';
  }

  loadAll() {
    const empresa: IEmpresa = this.$localStorage.retrieve('empresa');
    if (this.currentSearch) {
      this.clienteService
        .search({
          page: this.page - 1,
          query: this.currentSearch,
          size: this.itemsPerPage,
          sort: this.sort()
        })
        .subscribe(
          (res: HttpResponse<ICliente[]>) => this.paginateClientes(res.body, res.headers),
          (res: HttpErrorResponse) => this.onError(res.message)
        );
      return;
    }
    this.clienteService
      .queryByEmpresa(
        {
          page: this.page - 1,
          size: this.itemsPerPage,
          sort: this.sort()
        },
        empresa.id
      )
      .subscribe(
        (res: HttpResponse<ICliente[]>) => this.paginateClientes(res.body, res.headers),
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
    this.router.navigate(['/cliente'], {
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
      '/cliente',
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
      '/cliente',
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
    this.registerChangeInClientes();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ICliente) {
    return item.id;
  }

  registerChangeInClientes() {
    this.eventSubscriber = this.eventManager.subscribe('clienteListModification', response => this.loadAll());
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateClientes(data: ICliente[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.clientes = data;
    this.dataSource = new MatTableDataSource<ICliente>(this.clientes);
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  onPaginateChange(event: PageEvent) {
    this.page = event.pageIndex + 1;
    this.loadPage(event.pageIndex + 1);
  }

  goPuntoDeVenta(cliente: ICliente) {
    this.$localStorage.store('cliente', cliente);
    this.router.navigate(['/admin/admin/punto-de-venta']);
  }

  toggleFullWidth() {
    this.sidebarService.toggle();
    this.sidebarVisible = this.sidebarService.getStatus();
    this.cdr.detectChanges();
  }
}
