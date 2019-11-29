import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IPresentacion, Presentacion } from 'app/shared/model/presentacion.model';
import { PresentacionService } from './presentacion.service';
import { IProducto } from 'app/shared/model/producto.model';
import { ProductoService } from 'app/entities/producto/producto.service';
import { ILote } from 'app/shared/model/lote.model';
import { LoteService } from 'app/entities/lote/lote.service';

@Component({
  selector: 'jhi-presentacion-update',
  templateUrl: './presentacion-update.component.html'
})
export class PresentacionUpdateComponent implements OnInit {
  isSaving: boolean;

  productos: IProducto[];

  lotes: ILote[];
  fechaDp: any;

  editForm = this.fb.group({
    id: [],
    tipoPresentacion: [null, [Validators.required]],
    cantidad: [null, [Validators.required]],
    fecha: [null, [Validators.required]],
    costoUnitario: [null, [Validators.required]],
    precioVentaUnitario: [null, [Validators.required]],
    precioVentaTotal: [null, [Validators.required]],
    precioCostoTotal: [null, [Validators.required]],
    productoId: [],
    loteId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected presentacionService: PresentacionService,
    protected productoService: ProductoService,
    protected loteService: LoteService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ presentacion }) => {
      this.updateForm(presentacion);
    });
    this.productoService
      .query()
      .subscribe((res: HttpResponse<IProducto[]>) => (this.productos = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.loteService
      .query()
      .subscribe((res: HttpResponse<ILote[]>) => (this.lotes = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(presentacion: IPresentacion) {
    this.editForm.patchValue({
      id: presentacion.id,
      tipoPresentacion: presentacion.tipoPresentacion,
      cantidad: presentacion.cantidad,
      fecha: presentacion.fecha,
      costoUnitario: presentacion.costoUnitario,
      precioVentaUnitario: presentacion.precioVentaUnitario,
      precioVentaTotal: presentacion.precioVentaTotal,
      precioCostoTotal: presentacion.precioCostoTotal,
      productoId: presentacion.productoId,
      loteId: presentacion.loteId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const presentacion = this.createFromForm();
    if (presentacion.id !== undefined) {
      this.subscribeToSaveResponse(this.presentacionService.update(presentacion));
    } else {
      this.subscribeToSaveResponse(this.presentacionService.create(presentacion));
    }
  }

  private createFromForm(): IPresentacion {
    return {
      ...new Presentacion(),
      id: this.editForm.get(['id']).value,
      tipoPresentacion: this.editForm.get(['tipoPresentacion']).value,
      cantidad: this.editForm.get(['cantidad']).value,
      fecha: this.editForm.get(['fecha']).value,
      costoUnitario: this.editForm.get(['costoUnitario']).value,
      precioVentaUnitario: this.editForm.get(['precioVentaUnitario']).value,
      precioVentaTotal: this.editForm.get(['precioVentaTotal']).value,
      precioCostoTotal: this.editForm.get(['precioCostoTotal']).value,
      productoId: this.editForm.get(['productoId']).value,
      loteId: this.editForm.get(['loteId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPresentacion>>) {
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

  trackProductoById(index: number, item: IProducto) {
    return item.id;
  }

  trackLoteById(index: number, item: ILote) {
    return item.id;
  }
}
