import { Component, OnDestroy, OnInit } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiAlertService, JhiDataUtils, JhiEventManager, JhiParseLinks } from 'ng-jhipster';

import { ICaja, TipoMovimientoCaja } from 'app/shared/model/caja.model';
import { AccountService } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { CajaService } from './caja.service';
import { LocalStorageService } from 'ngx-webstorage';
import { IEmpresa } from 'app/shared/model/empresa.model';
import { NgxUiLoaderService } from 'ngx-ui-loader';
import { MatTableDataSource, PageEvent } from '@angular/material';

@Component({
    selector: 'jhi-caja',
    templateUrl: './caja.component.html'
})
export class CajaComponent implements OnInit, OnDestroy {
    currentAccount: any;
    cajas: ICaja[];
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
    saldo: number;
    dataSource: any;
    displayedColumns: string[] = [
        'id',
        'tipoMovimiento',
        'tipoPago',
        'descripcion',
        'saldoCtaCte',
        'importe',
        'fecha',
        'proveedorNombreProveedor',
        'clienteNombeApellido',
        'actions'
    ];
    pageEvent: PageEvent;

    constructor(
        protected cajaService: CajaService,
        protected parseLinks: JhiParseLinks,
        protected jhiAlertService: JhiAlertService,
        protected accountService: AccountService,
        protected activatedRoute: ActivatedRoute,
        protected dataUtils: JhiDataUtils,
        protected router: Router,
        protected eventManager: JhiEventManager,
        private $localStorage: LocalStorageService,
        private ngxLoader: NgxUiLoaderService
    ) {
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.routeData = this.activatedRoute.data.subscribe(data => {
            this.page = data.pagingParams.page;
            this.previousPage = data.pagingParams.page;
            this.reverse = data.pagingParams.ascending;
            this.predicate = data.pagingParams.predicate;
        });
        this.currentSearch =
            this.activatedRoute.snapshot && this.activatedRoute.snapshot.params['search']
                ? this.activatedRoute.snapshot.params['search']
                : '';
    }

    loadAll() {
        this.ngxLoader.start();
        const empresa: IEmpresa = this.$localStorage.retrieve('empresa');
        if (this.currentSearch) {
            this.cajaService
                .search({
                    page: this.page - 1,
                    query: this.currentSearch,
                    size: this.itemsPerPage,
                    sort: this.sort()
                })
                .subscribe(
                    (res: HttpResponse<ICaja[]>) => this.paginateCajas(res.body, res.headers),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
        }
        this.cajaService
            .queryByEmpresa(
                {
                    page: this.page - 1,
                    size: this.itemsPerPage,
                    sort: this.sort()
                },
                empresa.id
            )
            .subscribe(
                (res: HttpResponse<ICaja[]>) => this.paginateCajas(res.body, res.headers),
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
        this.router.navigate(['/caja'], {
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
            '/caja',
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
            '/caja',
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
        this.registerChangeInCajas();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ICaja) {
        return item.id;
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    registerChangeInCajas() {
        this.eventSubscriber = this.eventManager.subscribe('cajaListModification', response => this.loadAll());
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    protected paginateCajas(data: ICaja[], headers: HttpHeaders) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
        this.cajas = data;
        this.saldo = 0;
        this.cajas.forEach(caja => {
            if (caja.tipoMovimiento === TipoMovimientoCaja.INGRESO) {
                this.saldo = this.saldo + caja.importe;
            } else {
                this.saldo = this.saldo - caja.importe;
            }
        });
        this.dataSource = new MatTableDataSource<ICaja>(this.cajas);
        this.ngxLoader.stop();
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    onPaginateChange(event: PageEvent) {
        this.page = event.pageIndex + 1;
        this.loadPage(event.pageIndex + 1);
    }
}
