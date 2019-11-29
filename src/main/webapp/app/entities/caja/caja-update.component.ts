import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { ICaja, Caja } from 'app/shared/model/caja.model';
import { CajaService } from './caja.service';
import { IProveedor } from 'app/shared/model/proveedor.model';
import { ProveedorService } from 'app/entities/proveedor/proveedor.service';
import { ICliente } from 'app/shared/model/cliente.model';
import { ClienteService } from 'app/entities/cliente/cliente.service';
import { IEmpresa } from 'app/shared/model/empresa.model';
import { EmpresaService } from 'app/entities/empresa/empresa.service';

@Component({
  selector: 'jhi-caja-update',
  templateUrl: './caja-update.component.html'
})
export class CajaUpdateComponent implements OnInit {
  isSaving: boolean;

  proveedors: IProveedor[];

  clientes: ICliente[];

  empresas: IEmpresa[];
  fechaDp: any;

  editForm = this.fb.group({
    id: [],
    tipoMovimiento: [null, [Validators.required]],
    tipoPago: [null, [Validators.required]],
    descripcion: [],
    saldoCtaCte: [],
    importe: [null, [Validators.required]],
    fecha: [null, [Validators.required]],
    proveedorId: [],
    clienteId: [],
    empresaId: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected cajaService: CajaService,
    protected proveedorService: ProveedorService,
    protected clienteService: ClienteService,
    protected empresaService: EmpresaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ caja }) => {
      this.updateForm(caja);
    });
    this.proveedorService
      .query()
      .subscribe((res: HttpResponse<IProveedor[]>) => (this.proveedors = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.clienteService
      .query()
      .subscribe((res: HttpResponse<ICliente[]>) => (this.clientes = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.empresaService
      .query()
      .subscribe((res: HttpResponse<IEmpresa[]>) => (this.empresas = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(caja: ICaja) {
    this.editForm.patchValue({
      id: caja.id,
      tipoMovimiento: caja.tipoMovimiento,
      tipoPago: caja.tipoPago,
      descripcion: caja.descripcion,
      saldoCtaCte: caja.saldoCtaCte,
      importe: caja.importe,
      fecha: caja.fecha,
      proveedorId: caja.proveedorId,
      clienteId: caja.clienteId,
      empresaId: caja.empresaId
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  setFileData(event, field: string, isImage) {
    return new Promise((resolve, reject) => {
      if (event && event.target && event.target.files && event.target.files[0]) {
        const file: File = event.target.files[0];
        if (isImage && !file.type.startsWith('image/')) {
          reject(`File was expected to be an image but was found to be ${file.type}`);
        } else {
          const filedContentType: string = field + 'ContentType';
          this.dataUtils.toBase64(file, base64Data => {
            this.editForm.patchValue({
              [field]: base64Data,
              [filedContentType]: file.type
            });
          });
        }
      } else {
        reject(`Base64 data was not set as file could not be extracted from passed parameter: ${event}`);
      }
    }).then(
      // eslint-disable-next-line no-console
      () => console.log('blob added'), // success
      this.onError
    );
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const caja = this.createFromForm();
    if (caja.id !== undefined) {
      this.subscribeToSaveResponse(this.cajaService.update(caja));
    } else {
      this.subscribeToSaveResponse(this.cajaService.create(caja));
    }
  }

  private createFromForm(): ICaja {
    return {
      ...new Caja(),
      id: this.editForm.get(['id']).value,
      tipoMovimiento: this.editForm.get(['tipoMovimiento']).value,
      tipoPago: this.editForm.get(['tipoPago']).value,
      descripcion: this.editForm.get(['descripcion']).value,
      saldoCtaCte: this.editForm.get(['saldoCtaCte']).value,
      importe: this.editForm.get(['importe']).value,
      fecha: this.editForm.get(['fecha']).value,
      proveedorId: this.editForm.get(['proveedorId']).value,
      clienteId: this.editForm.get(['clienteId']).value,
      empresaId: this.editForm.get(['empresaId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICaja>>) {
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

  trackClienteById(index: number, item: ICliente) {
    return item.id;
  }

  trackEmpresaById(index: number, item: IEmpresa) {
    return item.id;
  }
}
