import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { ITanque } from 'app/shared/model/tanque.model';
import { MovimientoTanqueService } from '../movimiento-tanque';
import { IMovimientoTanque } from '../../shared/model/movimiento-tanque.model';
import { PageEvent } from '@angular/material/paginator';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { JhiAlertService, JhiParseLinks } from 'ng-jhipster';
import { MatTableDataSource } from '@angular/material/table';

@Component({
  selector: 'jhi-tanque-detail',
  templateUrl: './tanque-detail.component.html'
})
export class TanqueDetailComponent implements OnInit {
  tanque: ITanque;
  movimientoTanques: IMovimientoTanque[];
  links: any;
  totalItems: any;
  itemsPerPage: any;
  page: any;
  predicate: any;
  previousPage: any;
  reverse: any;
  dataSource: any;
  displayedColumns: string[] = ['fecha', 'estado', 'dias', 'tanque', 'producto', 'lote'];
  pageEvent: PageEvent;
  public sidebarVisible = true;
  constructor(
    protected activatedRoute: ActivatedRoute,
    protected jhiAlertService: JhiAlertService,
    protected parseLinks: JhiParseLinks,
    protected router: Router,
    protected movimientoTanqueService: MovimientoTanqueService
  ) {
    this.itemsPerPage = 10;
  }

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ tanque }) => {
      this.tanque = tanque;
      this.page = 1;
      if (this.tanque.id) {
        this.loadAll();
      }
    });
  }

  loadAll() {
    this.movimientoTanqueService
      .queryByTanque(
        {
          page: this.page - 1,
          size: this.itemsPerPage
        },
        this.tanque.id
      )
      .subscribe(
        (res: HttpResponse<IMovimientoTanque[]>) => this.paginateMovimientoTanques(res.body, res.headers),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  protected paginateMovimientoTanques(data: IMovimientoTanque[], headers: HttpHeaders) {
    console.log(data);
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.movimientoTanques = data;
    this.dataSource = new MatTableDataSource<IMovimientoTanque>(this.movimientoTanques);
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  transition() {
    // this.router.navigate(['/tanque'], {
    //   queryParams: {
    //     page: this.page,
    //     size: this.itemsPerPage,
    //     sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
    //   }
    // });
    this.loadAll();
  }

  loadPage(page: number) {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  onPaginateChange(event: PageEvent) {
    this.page = event.pageIndex + 1;
    this.loadPage(event.pageIndex + 1);
  }

  previousState() {
    window.history.back();
  }
}
