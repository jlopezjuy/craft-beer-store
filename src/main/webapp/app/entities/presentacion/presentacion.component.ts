import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { IPresentacion } from 'app/shared/model/presentacion.model';
import { AccountService } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { PresentacionService } from './presentacion.service';
import { LocalStorageService } from 'ngx-webstorage';
import { IProducto } from 'app/shared/model/producto.model';
import { NgxUiLoaderService } from 'ngx-ui-loader';
import { MatTableDataSource, PageEvent } from '@angular/material';

@Component({
    selector: 'jhi-presentacion',
    templateUrl: './presentacion.component.html',
    styleUrls: ['presentacion.component.scss']
})
export class PresentacionComponent implements OnInit, OnDestroy {
    currentAccount: any;
    presentacions: IPresentacion[];
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
    producto: IProducto;
    dataSource: any;
    displayedColumns: string[] = [
        'id',
        'tipoPresentacion',
        'cantidad',
        'fecha',
        'costoUnitario',
        'precioVentaUnitario',
        'precioTotal',
        'precioCostoTotal',
        'actions'
    ];
    pageEvent: PageEvent;

    constructor(
        protected presentacionService: PresentacionService,
        protected parseLinks: JhiParseLinks,
        protected jhiAlertService: JhiAlertService,
        protected accountService: AccountService,
        protected activatedRoute: ActivatedRoute,
        protected router: Router,
        protected eventManager: JhiEventManager,
        private $localStorage: LocalStorageService,
        private ngxLoader: NgxUiLoaderService
    ) {
        this.itemsPerPage = 4;
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
        this.producto = this.$localStorage.retrieve('producto');
        if (this.currentSearch) {
            this.presentacionService
                .search({
                    page: this.page - 1,
                    query: this.currentSearch,
                    size: this.itemsPerPage,
                    sort: this.sort()
                })
                .subscribe(
                    (res: HttpResponse<IPresentacion[]>) => this.paginatePresentacions(res.body, res.headers),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
        }
        console.log(this.page);
        this.presentacionService
            .queryByProducto(
                {
                    page: this.page - 1,
                    size: this.itemsPerPage,
                    sort: this.sort()
                },
                this.producto.id
            )
            .subscribe(
                (res: HttpResponse<IPresentacion[]>) => this.paginatePresentacions(res.body, res.headers),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    loadPage(page: number) {
        console.log(page);
        console.log(page);
        console.log(this.previousPage);
        if (page !== this.previousPage) {
            this.previousPage = page;
            this.transition();
        }
    }

    transition() {
        console.log('entro en transition...');
        console.log(this.page);
        this.router.navigate(['/presentacion'], {
            queryParams: {
                page: this.page,
                size: this.itemsPerPage,
                search: this.currentSearch,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }
        });
        console.log('paso el navigate');
        this.loadAll();
    }

    clear() {
        this.page = 0;
        this.currentSearch = '';
        this.router.navigate([
            '/presentacion',
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
            '/presentacion',
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
        this.registerChangeInPresentacions();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IPresentacion) {
        return item.id;
    }

    registerChangeInPresentacions() {
        this.eventSubscriber = this.eventManager.subscribe('presentacionListModification', response => this.loadAll());
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    protected paginatePresentacions(data: IPresentacion[], headers: HttpHeaders) {
        console.log(headers);
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
        this.presentacions = data;
        this.ngxLoader.stop();
        this.dataSource = new MatTableDataSource<IPresentacion>(this.presentacions);
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    previousState() {
        this.$localStorage.clear('producto');
        // window.history.back();
        this.router.navigate(['/producto']);
    }

    onPaginateChange(event: PageEvent) {
        console.log(event);
        this.page = event.pageIndex + 1;
        this.loadPage(event.pageIndex + 1);
    }
}
