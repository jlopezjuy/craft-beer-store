import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { ICompraInsumoDetalle, CompraInsumoDetalle } from 'app/shared/model/compra-insumo-detalle.model';
import { CompraInsumoDetalleService } from './compra-insumo-detalle.service';
import { ICompraInsumo } from 'app/shared/model/compra-insumo.model';
import { CompraInsumoService } from 'app/entities/compra-insumo/compra-insumo.service';
import { IInsumoRecomendado } from 'app/shared/model/insumo-recomendado.model';
import { InsumoRecomendadoService } from 'app/entities/insumo-recomendado/insumo-recomendado.service';

@Component({
  selector: 'jhi-compra-insumo-detalle-update',
  templateUrl: './compra-insumo-detalle-update.component.html'
})
export class CompraInsumoDetalleUpdateComponent implements OnInit {
  isSaving: boolean;

  comprainsumos: ICompraInsumo[];

  insumorecomendados: IInsumoRecomendado[];

  editForm = this.fb.group({
    id: [],
    unidad: [],
    codigoReferencia: [],
    stock: [],
    precioUnitario: [],
    precioTotal: [],
    tipo: [],
    compraInsumoId: [],
    insumoRecomendadoId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected compraInsumoDetalleService: CompraInsumoDetalleService,
    protected compraInsumoService: CompraInsumoService,
    protected insumoRecomendadoService: InsumoRecomendadoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ compraInsumoDetalle }) => {
      this.updateForm(compraInsumoDetalle);
    });
    this.compraInsumoService
      .query()
      .subscribe(
        (res: HttpResponse<ICompraInsumo[]>) => (this.comprainsumos = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.insumoRecomendadoService
      .query()
      .subscribe(
        (res: HttpResponse<IInsumoRecomendado[]>) => (this.insumorecomendados = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(compraInsumoDetalle: ICompraInsumoDetalle) {
    this.editForm.patchValue({
      id: compraInsumoDetalle.id,
      unidad: compraInsumoDetalle.unidad,
      codigoReferencia: compraInsumoDetalle.codigoReferencia,
      stock: compraInsumoDetalle.stock,
      precioUnitario: compraInsumoDetalle.precioUnitario,
      precioTotal: compraInsumoDetalle.precioTotal,
      tipo: compraInsumoDetalle.tipo,
      compraInsumoId: compraInsumoDetalle.compraInsumoId,
      insumoRecomendadoId: compraInsumoDetalle.insumoRecomendadoId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const compraInsumoDetalle = this.createFromForm();
    if (compraInsumoDetalle.id !== undefined) {
      this.subscribeToSaveResponse(this.compraInsumoDetalleService.update(compraInsumoDetalle));
    } else {
      this.subscribeToSaveResponse(this.compraInsumoDetalleService.create(compraInsumoDetalle));
    }
  }

  private createFromForm(): ICompraInsumoDetalle {
    return {
      ...new CompraInsumoDetalle(),
      id: this.editForm.get(['id']).value,
      unidad: this.editForm.get(['unidad']).value,
      codigoReferencia: this.editForm.get(['codigoReferencia']).value,
      stock: this.editForm.get(['stock']).value,
      precioUnitario: this.editForm.get(['precioUnitario']).value,
      precioTotal: this.editForm.get(['precioTotal']).value,
      tipo: this.editForm.get(['tipo']).value,
      compraInsumoId: this.editForm.get(['compraInsumoId']).value,
      insumoRecomendadoId: this.editForm.get(['insumoRecomendadoId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICompraInsumoDetalle>>) {
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

  trackCompraInsumoById(index: number, item: ICompraInsumo) {
    return item.id;
  }

  trackInsumoRecomendadoById(index: number, item: IInsumoRecomendado) {
    return item.id;
  }
}
