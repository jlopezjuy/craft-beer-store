import { ChangeDetectorRef, Component, OnDestroy, OnInit } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiAlertService, JhiEventManager, JhiParseLinks } from 'ng-jhipster';

import { ILote } from 'app/shared/model/lote.model';
import { AccountService } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { LoteService } from './lote.service';
import { IEmpresa } from '../../shared/model/empresa.model';
import { LocalStorageService } from 'ngx-webstorage';
import { MatDialog, MatTableDataSource, PageEvent } from '@angular/material';
import { SidebarService } from '../../services/sidebar.service';
import { LoteDeleteDialogComponent } from './lote-delete-dialog.component';

@Component({
  selector: 'jhi-lote',
  templateUrl: './lote.component.html'
})
export class LoteComponent implements OnInit, OnDestroy {
  currentAccount: any;
  lotes: ILote[];
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
  empresa: IEmpresa;
  dataSource: any;
  displayedColumns: string[] = [
    'codigo',
    'fechaCoccion',
    'coccion',
    'descripcion',
    'descuentaStock',
    'estado',
    'litrosEstimados',
    'recetaNombre',
    'producto',
    'tanque',
    'litrosDisponible',
    'actions'
  ];
  pageEvent: PageEvent;
  public sidebarVisible = true;

  constructor(
    protected loteService: LoteService,
    protected parseLinks: JhiParseLinks,
    protected jhiAlertService: JhiAlertService,
    protected accountService: AccountService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    private $localStorage: LocalStorageService,
    public dialog: MatDialog,
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
    this.loteService
      .queryByEmpresa(
        {
          page: this.page - 1,
          size: this.itemsPerPage,
          sort: this.sort()
        },
        this.empresa.id
      )
      .subscribe(
        (res: HttpResponse<ILote[]>) => this.paginateLotes(res.body, res.headers),
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
    this.router.navigate(['/lote'], {
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
      '/lote',
      {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

  ngOnInit() {
    this.empresa = this.$localStorage.retrieve('empresa');
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInLotes();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ILote) {
    return item.id;
  }

  registerChangeInLotes() {
    this.eventSubscriber = this.eventManager.subscribe('loteListModification', response => this.loadAll());
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateLotes(data: ILote[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.lotes = data;
    this.dataSource = new MatTableDataSource<ILote>(this.lotes);
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  toggleFullWidth() {
    this.sidebarService.toggle();
    this.sidebarVisible = this.sidebarService.getStatus();
    this.cdr.detectChanges();
  }

  onPaginateChange(event: PageEvent) {
    this.page = event.pageIndex + 1;
    this.loadPage(event.pageIndex + 1);
  }

  deleteControl(lote: ILote): void {
    console.log(lote);
    const dialogRef = this.dialog.open(LoteDeleteDialogComponent, {
      width: '50%',
      data: {
        id: lote.id,
        codigo: lote.codigo
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
      console.log(result);
      this.loadAll();
    });
  }
}
