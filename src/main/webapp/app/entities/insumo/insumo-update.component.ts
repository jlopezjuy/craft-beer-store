import { Component, OnInit, ElementRef } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IInsumo, Insumo } from 'app/shared/model/insumo.model';
import { InsumoService } from './insumo.service';
import { IEmpresa } from 'app/shared/model/empresa.model';
import { EmpresaService } from 'app/entities/empresa/empresa.service';
import { IInsumoRecomendado } from 'app/shared/model/insumo-recomendado.model';
import { InsumoRecomendadoService } from 'app/entities/insumo-recomendado/insumo-recomendado.service';

@Component({
  selector: 'jhi-insumo-update',
  templateUrl: './insumo-update.component.html'
})
export class InsumoUpdateComponent implements OnInit {
  isSaving: boolean;

  empresas: IEmpresa[];

  insumorecomendados: IInsumoRecomendado[];

  editForm = this.fb.group({
    id: [],
    nombreInsumo: [null, [Validators.required]],
    marca: [null, [Validators.required]],
    stock: [],
    unidad: [],
    tipo: [],
    imagen: [],
    imagenContentType: [],
    precioUnitario: [],
    precioTotal: [],
    empresaId: [],
    insumoRecomendadoId: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected insumoService: InsumoService,
    protected empresaService: EmpresaService,
    protected insumoRecomendadoService: InsumoRecomendadoService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ insumo }) => {
      this.updateForm(insumo);
    });
    this.empresaService
      .query()
      .subscribe((res: HttpResponse<IEmpresa[]>) => (this.empresas = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.insumoRecomendadoService
      .query()
      .subscribe(
        (res: HttpResponse<IInsumoRecomendado[]>) => (this.insumorecomendados = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(insumo: IInsumo) {
    this.editForm.patchValue({
      id: insumo.id,
      nombreInsumo: insumo.nombreInsumo,
      marca: insumo.marca,
      stock: insumo.stock,
      unidad: insumo.unidad,
      tipo: insumo.tipo,
      imagen: insumo.imagen,
      imagenContentType: insumo.imagenContentType,
      precioUnitario: insumo.precioUnitario,
      precioTotal: insumo.precioTotal,
      empresaId: insumo.empresaId,
      insumoRecomendadoId: insumo.insumoRecomendadoId
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
    const insumo = this.createFromForm();
    if (insumo.id !== undefined) {
      this.subscribeToSaveResponse(this.insumoService.update(insumo));
    } else {
      this.subscribeToSaveResponse(this.insumoService.create(insumo));
    }
  }

  private createFromForm(): IInsumo {
    return {
      ...new Insumo(),
      id: this.editForm.get(['id']).value,
      nombreInsumo: this.editForm.get(['nombreInsumo']).value,
      marca: this.editForm.get(['marca']).value,
      stock: this.editForm.get(['stock']).value,
      unidad: this.editForm.get(['unidad']).value,
      tipo: this.editForm.get(['tipo']).value,
      imagenContentType: this.editForm.get(['imagenContentType']).value,
      imagen: this.editForm.get(['imagen']).value,
      precioUnitario: this.editForm.get(['precioUnitario']).value,
      precioTotal: this.editForm.get(['precioTotal']).value,
      empresaId: this.editForm.get(['empresaId']).value,
      insumoRecomendadoId: this.editForm.get(['insumoRecomendadoId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInsumo>>) {
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

  trackInsumoRecomendadoById(index: number, item: IInsumoRecomendado) {
    return item.id;
  }
}
