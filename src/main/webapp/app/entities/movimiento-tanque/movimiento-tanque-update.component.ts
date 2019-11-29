import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IMovimientoTanque, MovimientoTanque } from 'app/shared/model/movimiento-tanque.model';
import { MovimientoTanqueService } from './movimiento-tanque.service';
import { ITanque } from 'app/shared/model/tanque.model';
import { TanqueService } from 'app/entities/tanque/tanque.service';
import { IProducto } from 'app/shared/model/producto.model';
import { ProductoService } from 'app/entities/producto/producto.service';
import { ILote } from 'app/shared/model/lote.model';
import { LoteService } from 'app/entities/lote/lote.service';

@Component({
  selector: 'jhi-movimiento-tanque-update',
  templateUrl: './movimiento-tanque-update.component.html'
})
export class MovimientoTanqueUpdateComponent implements OnInit {
  isSaving: boolean;

  tanques: ITanque[];

  productos: IProducto[];

  lotes: ILote[];
  fechaDp: any;

  editForm = this.fb.group({
    id: [],
    fecha: [],
    estado: [],
    dias: [],
    tanqueId: [],
    productoId: [],
    loteId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected movimientoTanqueService: MovimientoTanqueService,
    protected tanqueService: TanqueService,
    protected productoService: ProductoService,
    protected loteService: LoteService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ movimientoTanque }) => {
      this.updateForm(movimientoTanque);
    });
    this.tanqueService
      .query()
      .subscribe((res: HttpResponse<ITanque[]>) => (this.tanques = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.productoService
      .query()
      .subscribe((res: HttpResponse<IProducto[]>) => (this.productos = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.loteService
      .query()
      .subscribe((res: HttpResponse<ILote[]>) => (this.lotes = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(movimientoTanque: IMovimientoTanque) {
    this.editForm.patchValue({
      id: movimientoTanque.id,
      fecha: movimientoTanque.fecha,
      estado: movimientoTanque.estado,
      dias: movimientoTanque.dias,
      tanqueId: movimientoTanque.tanqueId,
      productoId: movimientoTanque.productoId,
      loteId: movimientoTanque.loteId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const movimientoTanque = this.createFromForm();
    if (movimientoTanque.id !== undefined) {
      this.subscribeToSaveResponse(this.movimientoTanqueService.update(movimientoTanque));
    } else {
      this.subscribeToSaveResponse(this.movimientoTanqueService.create(movimientoTanque));
    }
  }

  private createFromForm(): IMovimientoTanque {
    return {
      ...new MovimientoTanque(),
      id: this.editForm.get(['id']).value,
      fecha: this.editForm.get(['fecha']).value,
      estado: this.editForm.get(['estado']).value,
      dias: this.editForm.get(['dias']).value,
      tanqueId: this.editForm.get(['tanqueId']).value,
      productoId: this.editForm.get(['productoId']).value,
      loteId: this.editForm.get(['loteId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMovimientoTanque>>) {
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

  trackTanqueById(index: number, item: ITanque) {
    return item.id;
  }

  trackProductoById(index: number, item: IProducto) {
    return item.id;
  }

  trackLoteById(index: number, item: ILote) {
    return item.id;
  }
}
