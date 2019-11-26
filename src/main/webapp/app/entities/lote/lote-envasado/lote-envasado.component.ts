import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { SidebarService } from '../../../services/sidebar.service';
import { ActivatedRoute, Router } from '@angular/router';
import { EstadoLote, ILote } from '../../../shared/model/lote.model';
import { PresentacionService } from '../../presentacion';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { IPresentacion, Presentacion } from '../../../shared/model/presentacion.model';
import { MatTableDataSource } from '@angular/material/table';
import { JhiAlertService } from 'ng-jhipster';
import { PageEvent } from '@angular/material/paginator';
import * as moment from 'moment';
import { DATE_FORMAT } from '../../../shared';
import { MatDialog } from '@angular/material/dialog';
import { LoteEnvasadoDialogComponent } from './lote-envasado-dialog.component';

@Component({
  selector: 'jhi-lote-envasado',
  templateUrl: './lote-envasado.component.html',
  styles: []
})
export class LoteEnvasadoComponent implements OnInit {
  public sidebarVisible = true;
  lote: ILote;
  presentacions: IPresentacion[];
  presentacion: IPresentacion;
  totalItems: any;
  itemsPerPage: any;
  links: any;
  page: any;
  previousPage: any;
  predicate: any;
  reverse: any;
  fecha: any;
  dataSource: any;
  displayedColumns: string[] = [
    'tipoPresentacion',
    'cantidad',
    'fecha',
    'costoUnitario',
    'precioVentaUnitario',
    'precioVentaTotal',
    'precioCostoTotal',
    'action'
  ];
  pageEvent: PageEvent;

  constructor(
    protected activatedRoute: ActivatedRoute,
    private sidebarService: SidebarService,
    private cdr: ChangeDetectorRef,
    protected presentacionService: PresentacionService,
    protected jhiAlertService: JhiAlertService,
    protected router: Router,
    public dialog: MatDialog
  ) {
    this.page = 1;
    this.previousPage = 1;
    this.reverse = true;
    this.predicate = 'tipoPresentacion';
  }

  ngOnInit() {
    this.presentacion = new Presentacion();
    this.activatedRoute.data.subscribe(({ lote }) => {
      this.lote = lote;
      if (this.lote.id) {
        this.loadAll();
      }
    });
  }

  loadAll() {
    this.presentacionService
      .queryByLote(
        {
          page: this.page - 1,
          size: this.itemsPerPage,
          sort: this.sort()
        },
        this.lote.id
      )
      .subscribe(
        (res: HttpResponse<IPresentacion[]>) => this.paginatePresentacions(res.body, res.headers),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  toggleFullWidth() {
    this.sidebarService.toggle();
    this.sidebarVisible = this.sidebarService.getStatus();
    this.cdr.detectChanges();
  }

  protected paginatePresentacions(data: IPresentacion[], headers: HttpHeaders) {
    // this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.presentacions = data;
    console.log(this.presentacions);
    this.dataSource = new MatTableDataSource<IPresentacion>(this.presentacions);
  }

  onPaginateChange(event: PageEvent) {
    this.page = event.pageIndex + 1;
    this.loadPage(event.pageIndex + 1);
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
    this.router.navigate(['/envasado'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    });
    this.loadAll();
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  savePresentacion() {
    console.log(this.presentacion);
    if (this.validateAddBotella()) {
      this.presentacion.fecha = this.fecha != null ? moment(this.fecha, DATE_FORMAT) : null;
      this.presentacion.loteId = this.lote.id;
      this.presentacion.productoId = this.lote.productoId;
      this.presentacion.productoNombreComercial = this.lote.productoNombreComercial;
      this.presentacionService.create(this.presentacion).subscribe(respo => {
        console.log('create ok');
        this.loadAll();
        this.presentacion = new Presentacion();
        this.fecha = null;
      });
    }
  }

  openDialog(): void {
    const dialogRef = this.dialog.open(LoteEnvasadoDialogComponent, {
      width: '450px',
      data: this.lote
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
      console.log(result);
      if (result !== undefined) {
        console.log(this.presentacion);
        if (this.validateAddBotella()) {
          this.presentacion.fecha = this.fecha != null ? moment(this.fecha, DATE_FORMAT) : null;
          this.presentacion.loteId = this.lote.id;
          this.presentacion.productoId = this.lote.productoId;
          this.presentacion.productoNombreComercial = this.lote.productoNombreComercial;
          this.presentacionService.create(this.presentacion).subscribe(respo => {
            console.log('create ok');
            this.loadAll();
            this.presentacion = new Presentacion();
            this.fecha = null;
          });
        }
      }
      // this.animal = result;
    });
  }

  validateAddBotella(): boolean {
    let validate = true;
    validate =
      this.fecha !== undefined &&
      this.presentacion.tipoPresentacion !== undefined &&
      this.presentacion.cantidad !== undefined &&
      this.presentacion.precioVentaUnitario !== undefined &&
      this.presentacion.costoUnitario !== undefined;
    return validate;
  }

  deletePresentacion(presentacion: IPresentacion) {
    this.presentacionService.delete(presentacion.id).subscribe(resp => {
      console.log('delete ok');
      this.loadAll();
    });
  }

  changeCantidad() {
    if (this.presentacion.cantidad) {
      this.presentacion.precioCostoTotal = this.presentacion.costoUnitario * this.presentacion.cantidad;
    }
    if (this.presentacion.cantidad) {
      this.presentacion.precioVentaTotal = this.presentacion.precioVentaUnitario * this.presentacion.cantidad;
    }
  }

  changePrecioUnitario() {
    if (this.presentacion.cantidad) {
      this.presentacion.precioCostoTotal = this.presentacion.costoUnitario * this.presentacion.cantidad;
    }
  }

  changePrecioVenta() {
    if (this.presentacion.cantidad) {
      this.presentacion.precioVentaTotal = this.presentacion.precioVentaUnitario * this.presentacion.cantidad;
    }
  }

  previousState() {
    window.history.back();
  }

  clear() {
    this.presentacion = new Presentacion();
    this.fecha = null;
  }
}
