import { Component, OnInit, ElementRef } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IBarril, Barril } from 'app/shared/model/barril.model';
import { BarrilService } from './barril.service';
import { IEmpresa } from 'app/shared/model/empresa.model';
import { EmpresaService } from 'app/entities/empresa/empresa.service';
import { ILote } from 'app/shared/model/lote.model';
import { LoteService } from 'app/entities/lote/lote.service';
import { ICliente } from 'app/shared/model/cliente.model';
import { ClienteService } from 'app/entities/cliente/cliente.service';

@Component({
  selector: 'jhi-barril-update',
  templateUrl: './barril-update.component.html'
})
export class BarrilUpdateComponent implements OnInit {
  isSaving: boolean;

  empresas: IEmpresa[];

  lotes: ILote[];

  clientes: ICliente[];

  editForm = this.fb.group({
    id: [],
    codigo: [],
    litros: [],
    conector: [],
    estado: [],
    imagen: [],
    imagenContentType: [],
    empresaId: [],
    loteId: [],
    clienteId: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected barrilService: BarrilService,
    protected empresaService: EmpresaService,
    protected loteService: LoteService,
    protected clienteService: ClienteService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ barril }) => {
      this.updateForm(barril);
    });
    this.empresaService
      .query()
      .subscribe((res: HttpResponse<IEmpresa[]>) => (this.empresas = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.loteService
      .query()
      .subscribe((res: HttpResponse<ILote[]>) => (this.lotes = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.clienteService
      .query()
      .subscribe((res: HttpResponse<ICliente[]>) => (this.clientes = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(barril: IBarril) {
    this.editForm.patchValue({
      id: barril.id,
      codigo: barril.codigo,
      litros: barril.litros,
      conector: barril.conector,
      estado: barril.estado,
      imagen: barril.imagen,
      imagenContentType: barril.imagenContentType,
      empresaId: barril.empresaId,
      loteId: barril.loteId,
      clienteId: barril.clienteId
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
    const barril = this.createFromForm();
    if (barril.id !== undefined) {
      this.subscribeToSaveResponse(this.barrilService.update(barril));
    } else {
      this.subscribeToSaveResponse(this.barrilService.create(barril));
    }
  }

  private createFromForm(): IBarril {
    return {
      ...new Barril(),
      id: this.editForm.get(['id']).value,
      codigo: this.editForm.get(['codigo']).value,
      litros: this.editForm.get(['litros']).value,
      conector: this.editForm.get(['conector']).value,
      estado: this.editForm.get(['estado']).value,
      imagenContentType: this.editForm.get(['imagenContentType']).value,
      imagen: this.editForm.get(['imagen']).value,
      empresaId: this.editForm.get(['empresaId']).value,
      loteId: this.editForm.get(['loteId']).value,
      clienteId: this.editForm.get(['clienteId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBarril>>) {
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

  trackLoteById(index: number, item: ILote) {
    return item.id;
  }

  trackClienteById(index: number, item: ICliente) {
    return item.id;
  }
}
