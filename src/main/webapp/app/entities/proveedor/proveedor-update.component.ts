import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IProveedor, Proveedor } from 'app/shared/model/proveedor.model';
import { ProveedorService } from './proveedor.service';
import { IEmpresa } from 'app/shared/model/empresa.model';
import { EmpresaService } from 'app/entities/empresa/empresa.service';

@Component({
  selector: 'jhi-proveedor-update',
  templateUrl: './proveedor-update.component.html'
})
export class ProveedorUpdateComponent implements OnInit {
  isSaving: boolean;

  empresas: IEmpresa[];
  fechaAltaDp: any;

  editForm = this.fb.group({
    id: [],
    nombreProveedor: [null, [Validators.required]],
    razonSocial: [],
    cuit: [],
    telefono: [],
    fechaAlta: [null, [Validators.required]],
    domicilio: [],
    correo: [null, [Validators.pattern('^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$')]],
    notas: [],
    condicionFiscal: [],
    localidad: [],
    codigoPostal: [],
    provincia: [],
    contacto: [],
    empresaId: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected proveedorService: ProveedorService,
    protected empresaService: EmpresaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ proveedor }) => {
      this.updateForm(proveedor);
    });
    this.empresaService
      .query()
      .subscribe((res: HttpResponse<IEmpresa[]>) => (this.empresas = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(proveedor: IProveedor) {
    this.editForm.patchValue({
      id: proveedor.id,
      nombreProveedor: proveedor.nombreProveedor,
      razonSocial: proveedor.razonSocial,
      cuit: proveedor.cuit,
      telefono: proveedor.telefono,
      fechaAlta: proveedor.fechaAlta,
      domicilio: proveedor.domicilio,
      correo: proveedor.correo,
      notas: proveedor.notas,
      condicionFiscal: proveedor.condicionFiscal,
      localidad: proveedor.localidad,
      codigoPostal: proveedor.codigoPostal,
      provincia: proveedor.provincia,
      contacto: proveedor.contacto,
      empresaId: proveedor.empresaId
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
    const proveedor = this.createFromForm();
    if (proveedor.id !== undefined) {
      this.subscribeToSaveResponse(this.proveedorService.update(proveedor));
    } else {
      this.subscribeToSaveResponse(this.proveedorService.create(proveedor));
    }
  }

  private createFromForm(): IProveedor {
    return {
      ...new Proveedor(),
      id: this.editForm.get(['id']).value,
      nombreProveedor: this.editForm.get(['nombreProveedor']).value,
      razonSocial: this.editForm.get(['razonSocial']).value,
      cuit: this.editForm.get(['cuit']).value,
      telefono: this.editForm.get(['telefono']).value,
      fechaAlta: this.editForm.get(['fechaAlta']).value,
      domicilio: this.editForm.get(['domicilio']).value,
      correo: this.editForm.get(['correo']).value,
      notas: this.editForm.get(['notas']).value,
      condicionFiscal: this.editForm.get(['condicionFiscal']).value,
      localidad: this.editForm.get(['localidad']).value,
      codigoPostal: this.editForm.get(['codigoPostal']).value,
      provincia: this.editForm.get(['provincia']).value,
      contacto: this.editForm.get(['contacto']).value,
      empresaId: this.editForm.get(['empresaId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProveedor>>) {
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

  trackEmpresaById(index: number, item: IEmpresa) {
    return item.id;
  }
}
