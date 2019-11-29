import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IMovimientoBarril, MovimientoBarril } from 'app/shared/model/movimiento-barril.model';
import { MovimientoBarrilService } from './movimiento-barril.service';
import { IBarril } from 'app/shared/model/barril.model';
import { BarrilService } from 'app/entities/barril/barril.service';
import { IProducto } from 'app/shared/model/producto.model';
import { ProductoService } from 'app/entities/producto/producto.service';
import { ILote } from 'app/shared/model/lote.model';
import { LoteService } from 'app/entities/lote/lote.service';
import { ICliente } from 'app/shared/model/cliente.model';
import { ClienteService } from 'app/entities/cliente/cliente.service';

@Component({
  selector: 'jhi-movimiento-barril-update',
  templateUrl: './movimiento-barril-update.component.html'
})
export class MovimientoBarrilUpdateComponent implements OnInit {
  isSaving: boolean;

  barrils: IBarril[];

  productos: IProducto[];

  lotes: ILote[];

  clientes: ICliente[];
  fechaMovimientoDp: any;

  editForm = this.fb.group({
    id: [],
    fechaMovimiento: [],
    estado: [],
    dias: [],
    barrilId: [],
    productoId: [],
    loteId: [],
    clienteId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected movimientoBarrilService: MovimientoBarrilService,
    protected barrilService: BarrilService,
    protected productoService: ProductoService,
    protected loteService: LoteService,
    protected clienteService: ClienteService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ movimientoBarril }) => {
      this.updateForm(movimientoBarril);
    });
    this.barrilService
      .query()
      .subscribe((res: HttpResponse<IBarril[]>) => (this.barrils = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.productoService
      .query()
      .subscribe((res: HttpResponse<IProducto[]>) => (this.productos = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.loteService
      .query()
      .subscribe((res: HttpResponse<ILote[]>) => (this.lotes = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.clienteService
      .query()
      .subscribe((res: HttpResponse<ICliente[]>) => (this.clientes = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(movimientoBarril: IMovimientoBarril) {
    this.editForm.patchValue({
      id: movimientoBarril.id,
      fechaMovimiento: movimientoBarril.fechaMovimiento,
      estado: movimientoBarril.estado,
      dias: movimientoBarril.dias,
      barrilId: movimientoBarril.barrilId,
      productoId: movimientoBarril.productoId,
      loteId: movimientoBarril.loteId,
      clienteId: movimientoBarril.clienteId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const movimientoBarril = this.createFromForm();
    if (movimientoBarril.id !== undefined) {
      this.subscribeToSaveResponse(this.movimientoBarrilService.update(movimientoBarril));
    } else {
      this.subscribeToSaveResponse(this.movimientoBarrilService.create(movimientoBarril));
    }
  }

  private createFromForm(): IMovimientoBarril {
    return {
      ...new MovimientoBarril(),
      id: this.editForm.get(['id']).value,
      fechaMovimiento: this.editForm.get(['fechaMovimiento']).value,
      estado: this.editForm.get(['estado']).value,
      dias: this.editForm.get(['dias']).value,
      barrilId: this.editForm.get(['barrilId']).value,
      productoId: this.editForm.get(['productoId']).value,
      loteId: this.editForm.get(['loteId']).value,
      clienteId: this.editForm.get(['clienteId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMovimientoBarril>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
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
