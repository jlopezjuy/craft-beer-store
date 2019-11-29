import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IMovimientos, Movimientos } from 'app/shared/model/movimientos.model';
import { MovimientosService } from './movimientos.service';
import { ICliente } from 'app/shared/model/cliente.model';
import { ClienteService } from 'app/entities/cliente/cliente.service';
import { IEmpresa } from 'app/shared/model/empresa.model';
import { EmpresaService } from 'app/entities/empresa/empresa.service';
import { IPuntoDeVenta } from 'app/shared/model/punto-de-venta.model';
import { PuntoDeVentaService } from 'app/entities/punto-de-venta/punto-de-venta.service';

@Component({
  selector: 'jhi-movimientos-update',
  templateUrl: './movimientos-update.component.html'
})
export class MovimientosUpdateComponent implements OnInit {
  isSaving: boolean;

  clientes: ICliente[];

  empresas: IEmpresa[];

  puntodeventas: IPuntoDeVenta[];
  fechaMovimientoDp: any;

  editForm = this.fb.group({
    id: [],
    tipoMovimiento: [null, [Validators.required]],
    fechaMovimiento: [null, [Validators.required]],
    precioTotal: [null, [Validators.required]],
    numeroMovimiento: [null, [Validators.required]],
    estado: [null, [Validators.required]],
    litrosTotales: [],
    clienteId: [],
    empresaId: [],
    puntoDeVentaId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected movimientosService: MovimientosService,
    protected clienteService: ClienteService,
    protected empresaService: EmpresaService,
    protected puntoDeVentaService: PuntoDeVentaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ movimientos }) => {
      this.updateForm(movimientos);
    });
    this.clienteService
      .query()
      .subscribe((res: HttpResponse<ICliente[]>) => (this.clientes = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.empresaService
      .query()
      .subscribe((res: HttpResponse<IEmpresa[]>) => (this.empresas = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.puntoDeVentaService
      .query()
      .subscribe(
        (res: HttpResponse<IPuntoDeVenta[]>) => (this.puntodeventas = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(movimientos: IMovimientos) {
    this.editForm.patchValue({
      id: movimientos.id,
      tipoMovimiento: movimientos.tipoMovimiento,
      fechaMovimiento: movimientos.fechaMovimiento,
      precioTotal: movimientos.precioTotal,
      numeroMovimiento: movimientos.numeroMovimiento,
      estado: movimientos.estado,
      litrosTotales: movimientos.litrosTotales,
      clienteId: movimientos.clienteId,
      empresaId: movimientos.empresaId,
      puntoDeVentaId: movimientos.puntoDeVentaId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const movimientos = this.createFromForm();
    if (movimientos.id !== undefined) {
      this.subscribeToSaveResponse(this.movimientosService.update(movimientos));
    } else {
      this.subscribeToSaveResponse(this.movimientosService.create(movimientos));
    }
  }

  private createFromForm(): IMovimientos {
    return {
      ...new Movimientos(),
      id: this.editForm.get(['id']).value,
      tipoMovimiento: this.editForm.get(['tipoMovimiento']).value,
      fechaMovimiento: this.editForm.get(['fechaMovimiento']).value,
      precioTotal: this.editForm.get(['precioTotal']).value,
      numeroMovimiento: this.editForm.get(['numeroMovimiento']).value,
      estado: this.editForm.get(['estado']).value,
      litrosTotales: this.editForm.get(['litrosTotales']).value,
      clienteId: this.editForm.get(['clienteId']).value,
      empresaId: this.editForm.get(['empresaId']).value,
      puntoDeVentaId: this.editForm.get(['puntoDeVentaId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMovimientos>>) {
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

  trackClienteById(index: number, item: ICliente) {
    return item.id;
  }

  trackEmpresaById(index: number, item: IEmpresa) {
    return item.id;
  }

  trackPuntoDeVentaById(index: number, item: IPuntoDeVenta) {
    return item.id;
  }
}
