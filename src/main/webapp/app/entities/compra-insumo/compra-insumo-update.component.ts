import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { CompraInsumo, EstadoCompra, ICompraInsumo } from 'app/shared/model/compra-insumo.model';
import { CompraInsumoService } from './compra-insumo.service';
import { IProveedor } from 'app/shared/model/proveedor.model';
import { ProveedorService } from 'app/entities/proveedor';
import { IEmpresa } from 'app/shared/model/empresa.model';
import { EmpresaService } from 'app/account/settings/empresa';
import { DATE_FORMAT } from '../../shared';
import { LocalStorageService } from 'ngx-webstorage';
import { CompraInsumoDetalleService } from '../compra-insumo-detalle';
import { CompraInsumoDetalle, ICompraInsumoDetalle } from '../../shared/model/compra-insumo-detalle.model';
import { IInsumoRecomendado } from '../../shared/model/insumo-recomendado.model';
import { InsumoRecomendadoService } from '../insumo-recomendado';
import { MatTableDataSource } from '@angular/material/table';
import { ICliente } from '../../shared/model/cliente.model';

@Component({
  selector: 'jhi-compra-insumo-update',
  templateUrl: './compra-insumo-update.component.html'
})
export class CompraInsumoUpdateComponent implements OnInit {
  compraInsumo: ICompraInsumo;
  compraInsumoAux: ICompraInsumo;
  estadoCompra = EstadoCompra.PEDIDO_REALIZADO;
  isSaving: boolean;
  compraInsumoDetalles: ICompraInsumoDetalle[];
  compraInsumoDetalle: ICompraInsumoDetalle;
  insumorecomendados: IInsumoRecomendado[];
  proveedors: IProveedor[];
  empresa: IEmpresa;
  fechaDp: any;
  cantidadTotal: number;
  dataSource: any;
  displayedColumns: string[] = ['insumoRecomendado', 'unidad', 'tipo', 'codigoReferencia', 'stock', 'precioUnitario', 'precioTotal'];

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected compraInsumoService: CompraInsumoService,
    protected proveedorService: ProveedorService,
    protected empresaService: EmpresaService,
    protected activatedRoute: ActivatedRoute,
    protected $localStorage: LocalStorageService,
    protected compraInsumoDetalleService: CompraInsumoDetalleService,
    protected insumoRecomendadoService: InsumoRecomendadoService
  ) {}

  ngOnInit() {
    this.estadoCompra = EstadoCompra.PEDIDO_REALIZADO;
    this.compraInsumoDetalle = new CompraInsumoDetalle();
    this.cantidadTotal = 0;
    this.isSaving = false;
    this.empresa = this.$localStorage.retrieve('empresa');

    this.activatedRoute.data.subscribe(({ compraInsumo }) => {
      this.compraInsumo = compraInsumo;
      if (this.compraInsumo.id) {
        this.fechaDp = moment(this.compraInsumo.fecha, 'dd/MM/yyy').format();
        this.loadAllOnEdit();
        this.compraInsumoAux = new CompraInsumo();
        this.compraInsumoAux.estadoCompra = this.compraInsumo.estadoCompra;
      } else {
        this.compraInsumo = new CompraInsumo();
        this.compraInsumo.impuesto = 0;
        this.compraInsumo.total = 0;
        this.compraInsumo.subtotal = 0;
        this.compraInsumoDetalles = [];
        this.compraInsumoAux = new CompraInsumo();
        this.compraInsumoAux.estadoCompra = EstadoCompra.PEDIDO_REALIZADO;
      }
    });
    this.proveedorService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IProveedor[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProveedor[]>) => response.body)
      )
      .subscribe((res: IProveedor[]) => (this.proveedors = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.insumoRecomendadoService
      .queryAll()
      .pipe(
        filter((mayBeOk: HttpResponse<IInsumoRecomendado[]>) => mayBeOk.ok),
        map((response: HttpResponse<IInsumoRecomendado[]>) => response.body)
      )
      .subscribe((res: IInsumoRecomendado[]) => (this.insumorecomendados = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    this.compraInsumo.empresaId = this.empresa.id;
    this.calculoImportes();
    this.compraInsumo.fecha = this.fechaDp != null ? moment(this.fechaDp, DATE_FORMAT) : null;
    if (this.compraInsumo.id !== undefined) {
      this.subscribeToSaveResponse(this.compraInsumoService.update(this.compraInsumo));
    } else {
      this.subscribeToSaveResponse(this.compraInsumoService.create(this.compraInsumo));
    }
  }

  saveCompraInsumos(compraInsumoId: number) {
    this.compraInsumoDetalles.forEach(compra => {
      compra.compraInsumoId = compraInsumoId;
      compra.precioTotal = compra.precioUnitario * compra.stock + this.compraInsumo.gastoDeEnvio / this.cantidadTotal;
    });
    this.compraInsumoDetalleService.createList(this.compraInsumoDetalles).subscribe(resp => {
      console.log('ok');
    });
  }

  calculoImportes() {
    this.compraInsumo.subtotal = 0;
    this.compraInsumoDetalles.forEach(compra => {
      this.compraInsumo.subtotal = this.compraInsumo.subtotal + compra.precioUnitario * compra.stock;
    });
    this.compraInsumo.total =
      this.compraInsumo.subtotal + (this.compraInsumo.subtotal * this.compraInsumo.impuesto) / 100 + this.compraInsumo.gastoDeEnvio;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICompraInsumo>>) {
    result.subscribe((res: HttpResponse<ICompraInsumo>) => this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess(resp: ICompraInsumo) {
    this.saveCompraInsumos(resp.id);
    this.isSaving = false;
    this.cantidadTotal = 0;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackProveedorById(index: number, item: IProveedor) {
    return item.id;
  }

  trackEmpresaById(index: number, item: IEmpresa) {
    return item.id;
  }

  loadAllOnEdit() {
    if (this.compraInsumo.id) {
      this.compraInsumoService.find(this.compraInsumo.id).subscribe(resp => {
        this.compraInsumoAux = resp.body;
      });
    }

    this.compraInsumoDetalleService.findAllByCompraInsumo(this.compraInsumo.id).subscribe(response => {
      this.compraInsumoDetalles = response.body;
      this.compraInsumoDetalles.forEach(det => {
        this.cantidadTotal = this.cantidadTotal + det.stock;
      });
      this.dataSource = new MatTableDataSource<ICompraInsumoDetalle>(this.compraInsumoDetalles);
    });
  }

  trackId(index: number, item: ICompraInsumoDetalle) {
    return item.id;
  }

  trackInsumoRecomendadoById(index: number, item: IInsumoRecomendado) {
    return item.id;
  }

  precioUnitarioChange() {
    if (this.compraInsumoDetalle.precioUnitario > 0) {
      this.compraInsumoDetalle.precioTotal = this.compraInsumoDetalle.precioUnitario * this.compraInsumoDetalle.stock;
    }
  }

  addInsumo() {
    if (this.compraInsumoDetalle.insumoRecomendadoId !== undefined) {
      this.cantidadTotal = this.cantidadTotal + this.compraInsumoDetalle.stock;
      this.compraInsumoDetalles.push(this.compraInsumoDetalle);
      this.dataSource = new MatTableDataSource<ICompraInsumoDetalle>(this.compraInsumoDetalles);
      console.log(this.compraInsumoDetalles);
      this.compraInsumo.subtotal = this.compraInsumo.subtotal + this.compraInsumoDetalle.precioTotal;
      this.compraInsumo.total =
        this.compraInsumo.subtotal + (this.compraInsumo.subtotal * this.compraInsumo.impuesto) / 100 + this.compraInsumo.gastoDeEnvio;
      this.compraInsumoDetalle = new CompraInsumoDetalle();
    }
  }
}
