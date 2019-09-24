import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { JhiAlertService, JhiDataUtils, JhiParseLinks } from 'ng-jhipster';

import { IBarril } from 'app/shared/model/barril.model';
import { MovimientoBarrilService } from '../movimiento-barril';
import { IMovimientoBarril } from '../../shared/model/movimiento-barril.model';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { MatTableDataSource, PageEvent } from '@angular/material';
import { SidebarService } from '../../services/sidebar.service';
import { ITEMS_PER_PAGE } from '../../shared';

@Component({
  selector: 'jhi-barril-detail',
  templateUrl: './barril-detail.component.html'
})
export class BarrilDetailComponent implements OnInit {
  barril: IBarril;
  movimientoBarrils: IMovimientoBarril[];
  links: any;
  totalItems: any;
  routeData: any;
  itemsPerPage: any;
  page: any;
  predicate: any;
  previousPage: any;
  reverse: any;
  dataSource: any;
  displayedColumns: string[] = ['fechaMovimiento', 'estado', 'dias', 'producto', 'lote', 'cliente'];
  pageEvent: PageEvent;
  public sidebarVisible = true;

  constructor(
    protected dataUtils: JhiDataUtils,
    protected parseLinks: JhiParseLinks,
    protected jhiAlertService: JhiAlertService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected movimientoBarrilService: MovimientoBarrilService,
    private sidebarService: SidebarService,
    private cdr: ChangeDetectorRef
  ) {
    //page=0&size=20&sort=id,asc
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.routeData = this.activatedRoute.data.subscribe(data => {
      this.page = 1;
      this.previousPage = 1;
      this.reverse = true;
      this.predicate = 'id';
    });
  }

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ barril }) => {
      this.barril = barril;
      if (this.barril.id) {
        this.loadMovimientos();
      }
    });
  }

  loadMovimientos() {
    this.movimientoBarrilService
      .queryByBarril(
        {
          page: this.page - 1,
          size: this.itemsPerPage,
          sort: this.sort()
        },
        this.barril.id
      )
      .subscribe(
        (res: HttpResponse<IMovimientoBarril[]>) => this.paginateMovimientoBarrils(res.body, res.headers),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
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
    this.dataSource = new MatTableDataSource<IMovimientoBarril>(this.movimientoBarrils);
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
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
    this.loadMovimientos();
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }
  previousState() {
    window.history.back();
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
}
