import { Component, OnInit, OnDestroy, ChangeDetectorRef } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { ITanque } from 'app/shared/model/tanque.model';
import { AccountService } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { TanqueService } from './tanque.service';
import { LocalStorageService } from 'ngx-webstorage';
import { IEmpresa } from '../../shared/model/empresa.model';
import { MatDialog, MatTableDataSource, PageEvent } from '@angular/material';
import { SidebarService } from '../../services/sidebar.service';
import { TanqueDeleteDialogComponent } from './tanque-delete-dialog.component';

@Component({
  selector: 'jhi-tanque',
  templateUrl: './tanque.component.html'
})
export class TanqueComponent implements OnInit, OnDestroy {
  currentAccount: any;
  tanques: ITanque[];
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
    'nombre',
    'litros',
    'tipo',
    'estado',
    'listrosDisponible',
    'fechaIngreso',
    'loteCodigo',
    'productoNombreComercial',
    'actions'
  ];
  pageEvent: PageEvent;
  public sidebarVisible = true;

  constructor(
    protected tanqueService: TanqueService,
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
    this.tanqueService
      .queryByEmpresa(
        {
          page: this.page - 1,
          size: this.itemsPerPage,
          sort: this.sort()
        },
        this.empresa.id
      )
      .subscribe(
        (res: HttpResponse<ITanque[]>) => this.paginateTanques(res.body, res.headers),
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
    this.router.navigate(['/tanque'], {
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
      '/tanque',
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
    this.registerChangeInTanques();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ITanque) {
    return item.id;
  }

  registerChangeInTanques() {
    this.eventSubscriber = this.eventManager.subscribe('tanqueListModification', response => this.loadAll());
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateTanques(data: ITanque[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.tanques = data;
    this.dataSource = new MatTableDataSource<ITanque>(this.tanques);
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

  deleteControl(tanque: ITanque): void {
    console.log(tanque);
    const dialogRef = this.dialog.open(TanqueDeleteDialogComponent, {
      width: '50%',
      data: {
        id: tanque.id
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
      console.log(result);
      this.loadAll();
    });
  }
}
