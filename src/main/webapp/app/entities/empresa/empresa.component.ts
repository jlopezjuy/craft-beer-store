import { Component, OnInit, OnDestroy, ChangeDetectorRef } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { IEmpresa } from 'app/shared/model/empresa.model';
import { AccountService } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { EmpresaService } from './empresa.service';
import { MatTableDataSource, PageEvent } from '@angular/material';
import { EChartOption } from 'echarts';
import { SidebarService } from '../../services/sidebar.service';
import { SessionStorageService } from 'ngx-webstorage';

@Component({
    selector: 'jhi-empresa',
    templateUrl: './empresa.component.html'
})
export class EmpresaComponent implements OnInit, OnDestroy {
    currentAccount: any;
    empresas: IEmpresa[];
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
    displayedColumns: string[] = ['id', 'nombreEmpresa', 'direccion', 'telefono', 'correo', 'userLogin', 'actions'];
    pageEvent: PageEvent;
    public sidebarVisible = true;
    public visitorsOptions: EChartOption = {};
    public visitsOptions: EChartOption = {};
    public dropdownList: Array<any>;
    public selectedItems: Array<any>;
    public dropdownSettings: any;

    constructor(
        protected empresaService: EmpresaService,
        protected parseLinks: JhiParseLinks,
        protected jhiAlertService: JhiAlertService,
        protected accountService: AccountService,
        protected activatedRoute: ActivatedRoute,
        protected router: Router,
        protected eventManager: JhiEventManager,
        private sidebarService: SidebarService,
        private cdr: ChangeDetectorRef,
        private sessionStorage: SessionStorageService
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

        this.visitorsOptions = this.loadLineChartOptions([3, 5, 1, 6, 5, 4, 8, 3], '#49c5b6');
        this.visitsOptions = this.loadLineChartOptions([4, 6, 3, 2, 5, 6, 5, 4], '#f4516c');
        this.dropdownList = [
            { item_id: 1, item_text: 'Cheese' },
            { item_id: 2, item_text: 'Tomatoes' },
            { item_id: 3, item_text: 'Mozzarella' },
            { item_id: 4, item_text: 'Mushrooms' },
            { item_id: 5, item_text: 'Pepperoni' },
            { item_id: 6, item_text: 'Onions' }
        ];
        this.selectedItems = [{ item_id: 3, item_text: 'Mozzarella' }, { item_id: 4, item_text: 'Mushrooms' }];
        this.dropdownSettings = {
            singleSelection: false,
            idField: 'item_id',
            textField: 'item_text',
            selectAllText: 'Select All',
            unSelectAllText: 'UnSelect All',
            allowSearchFilter: true
        };
    }

    loadAll() {
        if (this.currentSearch) {
            this.empresaService
                .search({
                    page: this.page - 1,
                    query: this.currentSearch,
                    size: this.itemsPerPage,
                    sort: this.sort()
                })
                .subscribe(
                    (res: HttpResponse<IEmpresa[]>) => this.paginateEmpresas(res.body, res.headers),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
        }
        this.empresaService
            .query({
                page: this.page - 1,
                size: this.itemsPerPage,
                sort: this.sort()
            })
            .subscribe(
                (res: HttpResponse<IEmpresa[]>) => this.paginateEmpresas(res.body, res.headers),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    toggleFullWidth() {
        this.sidebarService.toggle();
        this.sidebarVisible = this.sidebarService.getStatus();
        this.cdr.detectChanges();
    }

    loadPage(page: number) {
        if (page !== this.previousPage) {
            this.previousPage = page;
            this.transition();
        }
    }

    transition() {
        this.router.navigate(['/empresa'], {
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
            '/empresa',
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
            '/empresa',
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
        this.registerChangeInEmpresas();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IEmpresa) {
        return item.id;
    }

    registerChangeInEmpresas() {
        this.eventSubscriber = this.eventManager.subscribe('empresaListModification', response => this.loadAll());
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    protected paginateEmpresas(data: IEmpresa[], headers: HttpHeaders) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
        this.empresas = data;
        this.dataSource = new MatTableDataSource<IEmpresa>(this.empresas);
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    onPaginateChange(event: PageEvent) {
        this.page = event.pageIndex + 1;
        this.loadPage(event.pageIndex + 1);
    }

    loadLineChartOptions(data, color) {
        let chartOption: EChartOption;
        let xAxisData: Array<any> = new Array<any>();

        data.forEach(element => {
            xAxisData.push('');
        });

        return (chartOption = {
            xAxis: {
                type: 'category',
                show: false,
                data: xAxisData,
                boundaryGap: false
            },
            yAxis: {
                type: 'value',
                show: false
            },
            tooltip: {
                trigger: 'axis',
                formatter: function(params, ticket, callback) {
                    return (
                        '<span style="display:inline-block;margin-right:5px;border-radius:10px;width:10px;height:10px;background-color:' +
                        color +
                        ';"></span>' +
                        params[0].value
                    );
                }
            },
            grid: {
                left: '0%',
                right: '0%',
                bottom: '0%',
                top: '0%',
                containLabel: false
            },
            series: [
                {
                    data: data,
                    type: 'line',
                    showSymbol: false,
                    symbolSize: 1,
                    lineStyle: {
                        color: color,
                        width: 1
                    }
                }
            ]
        });
    }
}
