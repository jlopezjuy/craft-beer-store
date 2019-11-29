import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { ILote, Lote } from 'app/shared/model/lote.model';
import { LoteService } from './lote.service';
import { IReceta } from 'app/shared/model/receta.model';
import { RecetaService } from 'app/entities/receta/receta.service';
import { IEmpresa } from 'app/shared/model/empresa.model';
import { EmpresaService } from 'app/entities/empresa/empresa.service';
import { IProducto } from 'app/shared/model/producto.model';
import { ProductoService } from 'app/entities/producto/producto.service';
import { ITanque } from 'app/shared/model/tanque.model';
import { TanqueService } from 'app/entities/tanque/tanque.service';

@Component({
  selector: 'jhi-lote-update',
  templateUrl: './lote-update.component.html'
})
export class LoteUpdateComponent implements OnInit {
  isSaving: boolean;

  recetas: IReceta[];

  empresas: IEmpresa[];

  productos: IProducto[];

  tanques: ITanque[];
  fechaCoccionDp: any;

  editForm = this.fb.group({
    id: [],
    codigo: [],
    fechaCoccion: [],
    coccion: [],
    descripcion: [],
    descuentaStock: [],
    estado: [],
    litrosEstimados: [],
    litrosEnTanque: [],
    litrosEnvasados: [],
    litrosDisponible: [],
    recetaId: [],
    empresaId: [],
    productoId: [],
    tanqueId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected loteService: LoteService,
    protected recetaService: RecetaService,
    protected empresaService: EmpresaService,
    protected productoService: ProductoService,
    protected tanqueService: TanqueService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ lote }) => {
      this.updateForm(lote);
    });
    this.recetaService
      .query()
      .subscribe((res: HttpResponse<IReceta[]>) => (this.recetas = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.empresaService
      .query()
      .subscribe((res: HttpResponse<IEmpresa[]>) => (this.empresas = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.productoService
      .query()
      .subscribe((res: HttpResponse<IProducto[]>) => (this.productos = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.tanqueService
      .query()
      .subscribe((res: HttpResponse<ITanque[]>) => (this.tanques = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(lote: ILote) {
    this.editForm.patchValue({
      id: lote.id,
      codigo: lote.codigo,
      fechaCoccion: lote.fechaCoccion,
      coccion: lote.coccion,
      descripcion: lote.descripcion,
      descuentaStock: lote.descuentaStock,
      estado: lote.estado,
      litrosEstimados: lote.litrosEstimados,
      litrosEnTanque: lote.litrosEnTanque,
      litrosEnvasados: lote.litrosEnvasados,
      litrosDisponible: lote.litrosDisponible,
      recetaId: lote.recetaId,
      empresaId: lote.empresaId,
      productoId: lote.productoId,
      tanqueId: lote.tanqueId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const lote = this.createFromForm();
    if (lote.id !== undefined) {
      this.subscribeToSaveResponse(this.loteService.update(lote));
    } else {
      this.subscribeToSaveResponse(this.loteService.create(lote));
    }
  }

  private createFromForm(): ILote {
    return {
      ...new Lote(),
      id: this.editForm.get(['id']).value,
      codigo: this.editForm.get(['codigo']).value,
      fechaCoccion: this.editForm.get(['fechaCoccion']).value,
      coccion: this.editForm.get(['coccion']).value,
      descripcion: this.editForm.get(['descripcion']).value,
      descuentaStock: this.editForm.get(['descuentaStock']).value,
      estado: this.editForm.get(['estado']).value,
      litrosEstimados: this.editForm.get(['litrosEstimados']).value,
      litrosEnTanque: this.editForm.get(['litrosEnTanque']).value,
      litrosEnvasados: this.editForm.get(['litrosEnvasados']).value,
      litrosDisponible: this.editForm.get(['litrosDisponible']).value,
      recetaId: this.editForm.get(['recetaId']).value,
      empresaId: this.editForm.get(['empresaId']).value,
      productoId: this.editForm.get(['productoId']).value,
      tanqueId: this.editForm.get(['tanqueId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILote>>) {
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

  trackRecetaById(index: number, item: IReceta) {
    return item.id;
  }

  trackEmpresaById(index: number, item: IEmpresa) {
    return item.id;
  }

  trackProductoById(index: number, item: IProducto) {
    return item.id;
  }

  trackTanqueById(index: number, item: ITanque) {
    return item.id;
  }
}
