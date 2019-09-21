import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IMovimientoBarril } from 'app/shared/model/movimiento-barril.model';
import { MovimientoBarrilService } from './movimiento-barril.service';
import { IBarril } from 'app/shared/model/barril.model';
import { BarrilService } from 'app/entities/barril';
import { IProducto } from 'app/shared/model/producto.model';
import { ProductoService } from 'app/entities/producto';
import { ILote } from 'app/shared/model/lote.model';
import { LoteService } from 'app/entities/lote';
import { ICliente } from 'app/shared/model/cliente.model';
import { ClienteService } from 'app/entities/cliente';

@Component({
  selector: 'jhi-movimiento-barril-update',
  templateUrl: './movimiento-barril-update.component.html'
})
export class MovimientoBarrilUpdateComponent implements OnInit {
  movimientoBarril: IMovimientoBarril;
  isSaving: boolean;

  barrils: IBarril[];

  productos: IProducto[];

  lotes: ILote[];

  clientes: ICliente[];
  fechaMovimientoDp: any;

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected movimientoBarrilService: MovimientoBarrilService,
    protected barrilService: BarrilService,
    protected productoService: ProductoService,
    protected loteService: LoteService,
    protected clienteService: ClienteService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ movimientoBarril }) => {
      this.movimientoBarril = movimientoBarril;
    });
    this.barrilService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IBarril[]>) => mayBeOk.ok),
        map((response: HttpResponse<IBarril[]>) => response.body)
      )
      .subscribe((res: IBarril[]) => (this.barrils = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.productoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IProducto[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProducto[]>) => response.body)
      )
      .subscribe((res: IProducto[]) => (this.productos = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.loteService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ILote[]>) => mayBeOk.ok),
        map((response: HttpResponse<ILote[]>) => response.body)
      )
      .subscribe((res: ILote[]) => (this.lotes = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.clienteService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICliente[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICliente[]>) => response.body)
      )
      .subscribe((res: ICliente[]) => (this.clientes = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    if (this.movimientoBarril.id !== undefined) {
      this.subscribeToSaveResponse(this.movimientoBarrilService.update(this.movimientoBarril));
    } else {
      this.subscribeToSaveResponse(this.movimientoBarrilService.create(this.movimientoBarril));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMovimientoBarril>>) {
    result.subscribe((res: HttpResponse<IMovimientoBarril>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackBarrilById(index: number, item: IBarril) {
    return item.id;
  }

  trackProductoById(index: number, item: IProducto) {
    return item.id;
  }

  trackLoteById(index: number, item: ILote) {
    return item.id;
  }

  trackClienteById(index: number, item: ICliente) {
    return item.id;
  }
}
