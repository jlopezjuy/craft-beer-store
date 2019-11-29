import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { ICompraInsumo, CompraInsumo } from 'app/shared/model/compra-insumo.model';
import { CompraInsumoService } from './compra-insumo.service';
import { IProveedor } from 'app/shared/model/proveedor.model';
import { ProveedorService } from 'app/entities/proveedor/proveedor.service';
import { IEmpresa } from 'app/shared/model/empresa.model';
import { EmpresaService } from 'app/entities/empresa/empresa.service';

@Component({
  selector: 'jhi-compra-insumo-update',
  templateUrl: './compra-insumo-update.component.html'
})
export class CompraInsumoUpdateComponent implements OnInit {
  isSaving: boolean;

  proveedors: IProveedor[];

  empresas: IEmpresa[];
  fechaDp: any;

  editForm = this.fb.group({
    id: [],
    nroFactura: [],
    fecha: [],
    subtotal: [],
    gastoDeEnvio: [],
    impuesto: [],
    total: [],
    estadoCompra: [],
    proveedorId: [],
    empresaId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected compraInsumoService: CompraInsumoService,
    protected proveedorService: ProveedorService,
    protected empresaService: EmpresaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ compraInsumo }) => {
      this.updateForm(compraInsumo);
    });
    this.proveedorService
      .query()
      .subscribe((res: HttpResponse<IProveedor[]>) => (this.proveedors = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.empresaService
      .query()
      .subscribe((res: HttpResponse<IEmpresa[]>) => (this.empresas = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(compraInsumo: ICompraInsumo) {
    this.editForm.patchValue({
      id: compraInsumo.id,
      nroFactura: compraInsumo.nroFactura,
      fecha: compraInsumo.fecha,
      subtotal: compraInsumo.subtotal,
      gastoDeEnvio: compraInsumo.gastoDeEnvio,
      impuesto: compraInsumo.impuesto,
      total: compraInsumo.total,
      estadoCompra: compraInsumo.estadoCompra,
      proveedorId: compraInsumo.proveedorId,
      empresaId: compraInsumo.empresaId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const compraInsumo = this.createFromForm();
    if (compraInsumo.id !== undefined) {
      this.subscribeToSaveResponse(this.compraInsumoService.update(compraInsumo));
    } else {
      this.subscribeToSaveResponse(this.compraInsumoService.create(compraInsumo));
    }
  }

  private createFromForm(): ICompraInsumo {
    return {
      ...new CompraInsumo(),
      id: this.editForm.get(['id']).value,
      nroFactura: this.editForm.get(['nroFactura']).value,
      fecha: this.editForm.get(['fecha']).value,
      subtotal: this.editForm.get(['subtotal']).value,
      gastoDeEnvio: this.editForm.get(['gastoDeEnvio']).value,
      impuesto: this.editForm.get(['impuesto']).value,
      total: this.editForm.get(['total']).value,
      estadoCompra: this.editForm.get(['estadoCompra']).value,
      proveedorId: this.editForm.get(['proveedorId']).value,
      empresaId: this.editForm.get(['empresaId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICompraInsumo>>) {
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

  trackProveedorById(index: number, item: IProveedor) {
    return item.id;
  }

  trackEmpresaById(index: number, item: IEmpresa) {
    return item.id;
  }
}
