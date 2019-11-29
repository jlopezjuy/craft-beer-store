import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IPuntoDeVenta, PuntoDeVenta } from 'app/shared/model/punto-de-venta.model';
import { PuntoDeVentaService } from './punto-de-venta.service';
import { ICliente } from 'app/shared/model/cliente.model';
import { ClienteService } from 'app/entities/cliente/cliente.service';

@Component({
  selector: 'jhi-punto-de-venta-update',
  templateUrl: './punto-de-venta-update.component.html'
})
export class PuntoDeVentaUpdateComponent implements OnInit {
  isSaving: boolean;

  clientes: ICliente[];

  editForm = this.fb.group({
    id: [],
    nombre: [null, [Validators.required]],
    direccionDeEntrega: [null, [Validators.required]],
    localidad: [null, [Validators.required]],
    notas: [],
    clienteId: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected puntoDeVentaService: PuntoDeVentaService,
    protected clienteService: ClienteService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ puntoDeVenta }) => {
      this.updateForm(puntoDeVenta);
    });
    this.clienteService
      .query()
      .subscribe((res: HttpResponse<ICliente[]>) => (this.clientes = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(puntoDeVenta: IPuntoDeVenta) {
    this.editForm.patchValue({
      id: puntoDeVenta.id,
      nombre: puntoDeVenta.nombre,
      direccionDeEntrega: puntoDeVenta.direccionDeEntrega,
      localidad: puntoDeVenta.localidad,
      notas: puntoDeVenta.notas,
      clienteId: puntoDeVenta.clienteId
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
    const puntoDeVenta = this.createFromForm();
    if (puntoDeVenta.id !== undefined) {
      this.subscribeToSaveResponse(this.puntoDeVentaService.update(puntoDeVenta));
    } else {
      this.subscribeToSaveResponse(this.puntoDeVentaService.create(puntoDeVenta));
    }
  }

  private createFromForm(): IPuntoDeVenta {
    return {
      ...new PuntoDeVenta(),
      id: this.editForm.get(['id']).value,
      nombre: this.editForm.get(['nombre']).value,
      direccionDeEntrega: this.editForm.get(['direccionDeEntrega']).value,
      localidad: this.editForm.get(['localidad']).value,
      notas: this.editForm.get(['notas']).value,
      clienteId: this.editForm.get(['clienteId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPuntoDeVenta>>) {
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
}
