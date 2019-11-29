import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { IDetalleMovimiento, DetalleMovimiento } from 'app/shared/model/detalle-movimiento.model';
import { DetalleMovimientoService } from './detalle-movimiento.service';
import { IPresentacion } from 'app/shared/model/presentacion.model';
import { PresentacionService } from 'app/entities/presentacion/presentacion.service';
import { IMovimientos } from 'app/shared/model/movimientos.model';
import { MovimientosService } from 'app/entities/movimientos/movimientos.service';

@Component({
  selector: 'jhi-detalle-movimiento-update',
  templateUrl: './detalle-movimiento-update.component.html'
})
export class DetalleMovimientoUpdateComponent implements OnInit {
  isSaving: boolean;

  presentacions: IPresentacion[];

  movimientos: IMovimientos[];

  editForm = this.fb.group({
    id: [],
    cantidad: [null, [Validators.required]],
    precioTotal: [null, [Validators.required]],
    presentacionId: [],
    movimientosId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected detalleMovimientoService: DetalleMovimientoService,
    protected presentacionService: PresentacionService,
    protected movimientosService: MovimientosService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ detalleMovimiento }) => {
      this.updateForm(detalleMovimiento);
    });
    this.presentacionService
      .query()
      .subscribe(
        (res: HttpResponse<IPresentacion[]>) => (this.presentacions = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.movimientosService
      .query()
      .subscribe(
        (res: HttpResponse<IMovimientos[]>) => (this.movimientos = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(detalleMovimiento: IDetalleMovimiento) {
    this.editForm.patchValue({
      id: detalleMovimiento.id,
      cantidad: detalleMovimiento.cantidad,
      precioTotal: detalleMovimiento.precioTotal,
      presentacionId: detalleMovimiento.presentacionId,
      movimientosId: detalleMovimiento.movimientosId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const detalleMovimiento = this.createFromForm();
    if (detalleMovimiento.id !== undefined) {
      this.subscribeToSaveResponse(this.detalleMovimientoService.update(detalleMovimiento));
    } else {
      this.subscribeToSaveResponse(this.detalleMovimientoService.create(detalleMovimiento));
    }
  }

  private createFromForm(): IDetalleMovimiento {
    return {
      ...new DetalleMovimiento(),
      id: this.editForm.get(['id']).value,
      cantidad: this.editForm.get(['cantidad']).value,
      precioTotal: this.editForm.get(['precioTotal']).value,
      presentacionId: this.editForm.get(['presentacionId']).value,
      movimientosId: this.editForm.get(['movimientosId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDetalleMovimiento>>) {
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

  trackPresentacionById(index: number, item: IPresentacion) {
    return item.id;
  }

  trackMovimientosById(index: number, item: IMovimientos) {
    return item.id;
  }
}
