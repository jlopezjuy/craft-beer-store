import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { IProveedor } from 'app/shared/model/proveedor.model';
import { AccountService } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { ProveedorService } from './proveedor.service';
import { LocalStorageService } from 'ngx-webstorage';
import { IEmpresa } from 'app/shared/model/empresa.model';
import { NgxUiLoaderService } from 'ngx-ui-loader';
import { MatTableDataSource, PageEvent } from '@angular/material';

@Component({
    selector: 'jhi-proveedor',
    templateUrl: './proveedor.component.html'
})
export class ProveedorComponent implements OnInit, OnDestroy {
    currentAccount: any;
    proveedors: IProveedor[];
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
    displayedColumns: string[] = [
        'id',
        'nombreProveedor',
        'razonSocial',
        'cuit',
        'telefono',
        'fechaAlta',
        'domicilio',
        'correo',
        'actions'
    ];
    pageEvent: PageEvent;

    constructor(
        protected proveedorService: ProveedorService,
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
            this.proveedorService
                .search({
                    page: this.page - 1,
                    query: this.currentSearch,
                    size: this.itemsPerPage,
                    sort: this.sort()
                })
                .subscribe(
                    (res: HttpResponse<IProveedor[]>) => this.paginateProveedors(res.body, res.headers),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
        }
        this.proveedorService
            .queryByEmpresa(
                {
                    page: this.page - 1,
                    size: this.itemsPerPage,
                    sort: this.sort()
                },
                empresa.id
            )
            .subscribe(
                (res: HttpResponse<IProveedor[]>) => this.paginateProveedors(res.body, res.headers),
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
        this.router.navigate(['/proveedor'], {
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
            '/proveedor',
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
            '/proveedor',
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
        this.registerChangeInProveedors();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IProveedor) {
        return item.id;
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    registerChangeInProveedors() {
        this.eventSubscriber = this.eventManager.subscribe('proveedorListModification', response => this.loadAll());
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    protected paginateProveedors(data: IProveedor[], headers: HttpHeaders) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
        this.proveedors = data;
        this.dataSource = new MatTableDataSource<IProveedor>(this.proveedors);
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
