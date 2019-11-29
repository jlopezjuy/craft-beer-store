import { Component, OnInit, ElementRef } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IEquipamiento, Equipamiento } from 'app/shared/model/equipamiento.model';
import { EquipamientoService } from './equipamiento.service';
import { IEmpresa } from 'app/shared/model/empresa.model';
import { EmpresaService } from 'app/entities/empresa/empresa.service';

@Component({
  selector: 'jhi-equipamiento-update',
  templateUrl: './equipamiento-update.component.html'
})
export class EquipamientoUpdateComponent implements OnInit {
  isSaving: boolean;

  empresas: IEmpresa[];
  fechaCompraDp: any;

  editForm = this.fb.group({
    id: [],
    nombreEquipamiento: [null, [Validators.required]],
    tipoEquipamiento: [null, [Validators.required]],
    precio: [null, [Validators.required, Validators.min(0)]],
    costoEnvio: [null, [Validators.required, Validators.min(0)]],
    fechaCompra: [null, [Validators.required]],
    imagen: [],
    imagenContentType: [],
    empresaId: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected equipamientoService: EquipamientoService,
    protected empresaService: EmpresaService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ equipamiento }) => {
      this.updateForm(equipamiento);
    });
    this.empresaService
      .query()
      .subscribe((res: HttpResponse<IEmpresa[]>) => (this.empresas = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(equipamiento: IEquipamiento) {
    this.editForm.patchValue({
      id: equipamiento.id,
      nombreEquipamiento: equipamiento.nombreEquipamiento,
      tipoEquipamiento: equipamiento.tipoEquipamiento,
      precio: equipamiento.precio,
      costoEnvio: equipamiento.costoEnvio,
      fechaCompra: equipamiento.fechaCompra,
      imagen: equipamiento.imagen,
      imagenContentType: equipamiento.imagenContentType,
      empresaId: equipamiento.empresaId
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

  clearInputImage(field: string, fieldContentType: string, idInput: string) {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null
    });
    if (this.elementRef && idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const equipamiento = this.createFromForm();
    if (equipamiento.id !== undefined) {
      this.subscribeToSaveResponse(this.equipamientoService.update(equipamiento));
    } else {
      this.subscribeToSaveResponse(this.equipamientoService.create(equipamiento));
    }
  }

  private createFromForm(): IEquipamiento {
    return {
      ...new Equipamiento(),
      id: this.editForm.get(['id']).value,
      nombreEquipamiento: this.editForm.get(['nombreEquipamiento']).value,
      tipoEquipamiento: this.editForm.get(['tipoEquipamiento']).value,
      precio: this.editForm.get(['precio']).value,
      costoEnvio: this.editForm.get(['costoEnvio']).value,
      fechaCompra: this.editForm.get(['fechaCompra']).value,
      imagenContentType: this.editForm.get(['imagenContentType']).value,
      imagen: this.editForm.get(['imagen']).value,
      empresaId: this.editForm.get(['empresaId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEquipamiento>>) {
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
