import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { ITanque, Tanque } from 'app/shared/model/tanque.model';
import { TanqueService } from './tanque.service';
import { ILote } from 'app/shared/model/lote.model';
import { LoteService } from 'app/entities/lote/lote.service';
import { IProducto } from 'app/shared/model/producto.model';
import { ProductoService } from 'app/entities/producto/producto.service';
import { IEmpresa } from 'app/shared/model/empresa.model';
import { EmpresaService } from 'app/entities/empresa/empresa.service';

@Component({
  selector: 'jhi-tanque-update',
  templateUrl: './tanque-update.component.html'
})
export class TanqueUpdateComponent implements OnInit {
  isSaving: boolean;

  lotes: ILote[];

  productos: IProducto[];

  empresas: IEmpresa[];
  fechaIngresoDp: any;

  editForm = this.fb.group({
    id: [],
    nombre: [],
    litros: [],
    tipo: [],
    estado: [],
    listrosDisponible: [],
    fechaIngreso: [],
    loteId: [],
    productoId: [],
    empresaId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected tanqueService: TanqueService,
    protected loteService: LoteService,
    protected productoService: ProductoService,
    protected empresaService: EmpresaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ tanque }) => {
      this.updateForm(tanque);
    });
    this.loteService
      .query()
      .subscribe((res: HttpResponse<ILote[]>) => (this.lotes = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.productoService
      .query()
      .subscribe((res: HttpResponse<IProducto[]>) => (this.productos = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.empresaService
      .query()
      .subscribe((res: HttpResponse<IEmpresa[]>) => (this.empresas = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(tanque: ITanque) {
    this.editForm.patchValue({
      id: tanque.id,
      nombre: tanque.nombre,
      litros: tanque.litros,
      tipo: tanque.tipo,
      estado: tanque.estado,
      listrosDisponible: tanque.listrosDisponible,
      fechaIngreso: tanque.fechaIngreso,
      loteId: tanque.loteId,
      productoId: tanque.productoId,
      empresaId: tanque.empresaId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const tanque = this.createFromForm();
    if (tanque.id !== undefined) {
      this.subscribeToSaveResponse(this.tanqueService.update(tanque));
    } else {
      this.subscribeToSaveResponse(this.tanqueService.create(tanque));
    }
  }

  private createFromForm(): ITanque {
    return {
      ...new Tanque(),
      id: this.editForm.get(['id']).value,
      nombre: this.editForm.get(['nombre']).value,
      litros: this.editForm.get(['litros']).value,
      tipo: this.editForm.get(['tipo']).value,
      estado: this.editForm.get(['estado']).value,
      listrosDisponible: this.editForm.get(['listrosDisponible']).value,
      fechaIngreso: this.editForm.get(['fechaIngreso']).value,
      loteId: this.editForm.get(['loteId']).value,
      productoId: this.editForm.get(['productoId']).value,
      empresaId: this.editForm.get(['empresaId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITanque>>) {
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

  trackLoteById(index: number, item: ILote) {
    return item.id;
  }

  trackProductoById(index: number, item: IProducto) {
    return item.id;
  }

  trackEmpresaById(index: number, item: IEmpresa) {
    return item.id;
  }
}
