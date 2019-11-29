import { Component, OnInit, ElementRef } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IProducto, Producto } from 'app/shared/model/producto.model';
import { ProductoService } from './producto.service';
import { IEmpresa } from 'app/shared/model/empresa.model';
import { EmpresaService } from 'app/entities/empresa/empresa.service';
import { IEstilos } from 'app/shared/model/estilos.model';
import { EstilosService } from 'app/entities/estilos/estilos.service';

@Component({
  selector: 'jhi-producto-update',
  templateUrl: './producto-update.component.html'
})
export class ProductoUpdateComponent implements OnInit {
  isSaving: boolean;

  empresas: IEmpresa[];

  estilos: IEstilos[];

  editForm = this.fb.group({
    id: [],
    descripcion: [null, [Validators.required]],
    tipo: [],
    nombreComercial: [],
    tipoProducto: [],
    imagen: [],
    imagenContentType: [],
    observacion: [],
    srmColor: [],
    empresaId: [],
    estilosId: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected productoService: ProductoService,
    protected empresaService: EmpresaService,
    protected estilosService: EstilosService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ producto }) => {
      this.updateForm(producto);
    });
    this.empresaService
      .query()
      .subscribe((res: HttpResponse<IEmpresa[]>) => (this.empresas = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.estilosService
      .query()
      .subscribe((res: HttpResponse<IEstilos[]>) => (this.estilos = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(producto: IProducto) {
    this.editForm.patchValue({
      id: producto.id,
      descripcion: producto.descripcion,
      tipo: producto.tipo,
      nombreComercial: producto.nombreComercial,
      tipoProducto: producto.tipoProducto,
      imagen: producto.imagen,
      imagenContentType: producto.imagenContentType,
      observacion: producto.observacion,
      srmColor: producto.srmColor,
      empresaId: producto.empresaId,
      estilosId: producto.estilosId
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
    const producto = this.createFromForm();
    if (producto.id !== undefined) {
      this.subscribeToSaveResponse(this.productoService.update(producto));
    } else {
      this.subscribeToSaveResponse(this.productoService.create(producto));
    }
  }

  private createFromForm(): IProducto {
    return {
      ...new Producto(),
      id: this.editForm.get(['id']).value,
      descripcion: this.editForm.get(['descripcion']).value,
      tipo: this.editForm.get(['tipo']).value,
      nombreComercial: this.editForm.get(['nombreComercial']).value,
      tipoProducto: this.editForm.get(['tipoProducto']).value,
      imagenContentType: this.editForm.get(['imagenContentType']).value,
      imagen: this.editForm.get(['imagen']).value,
      observacion: this.editForm.get(['observacion']).value,
      srmColor: this.editForm.get(['srmColor']).value,
      empresaId: this.editForm.get(['empresaId']).value,
      estilosId: this.editForm.get(['estilosId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProducto>>) {
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

  trackEstilosById(index: number, item: IEstilos) {
    return item.id;
  }
}
