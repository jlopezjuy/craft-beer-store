import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IMedicionLote, MedicionLote } from 'app/shared/model/medicion-lote.model';
import { MedicionLoteService } from './medicion-lote.service';
import { ILote } from 'app/shared/model/lote.model';
import { LoteService } from 'app/entities/lote/lote.service';
import { ITanque } from 'app/shared/model/tanque.model';
import { TanqueService } from 'app/entities/tanque/tanque.service';

@Component({
  selector: 'jhi-medicion-lote-update',
  templateUrl: './medicion-lote-update.component.html'
})
export class MedicionLoteUpdateComponent implements OnInit {
  isSaving: boolean;

  lotes: ILote[];

  tanques: ITanque[];

  editForm = this.fb.group({
    id: [],
    dia: [],
    tipoMedicion: [],
    estado: [],
    fechaRealizado: [],
    valor: [],
    observacion: [],
    loteId: [],
    tanqueId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected medicionLoteService: MedicionLoteService,
    protected loteService: LoteService,
    protected tanqueService: TanqueService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ medicionLote }) => {
      this.updateForm(medicionLote);
    });
    this.loteService
      .query()
      .subscribe((res: HttpResponse<ILote[]>) => (this.lotes = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.tanqueService
      .query()
      .subscribe((res: HttpResponse<ITanque[]>) => (this.tanques = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(medicionLote: IMedicionLote) {
    this.editForm.patchValue({
      id: medicionLote.id,
      dia: medicionLote.dia,
      tipoMedicion: medicionLote.tipoMedicion,
      estado: medicionLote.estado,
      fechaRealizado: medicionLote.fechaRealizado != null ? medicionLote.fechaRealizado.format(DATE_TIME_FORMAT) : null,
      valor: medicionLote.valor,
      observacion: medicionLote.observacion,
      loteId: medicionLote.loteId,
      tanqueId: medicionLote.tanqueId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const medicionLote = this.createFromForm();
    if (medicionLote.id !== undefined) {
      this.subscribeToSaveResponse(this.medicionLoteService.update(medicionLote));
    } else {
      this.subscribeToSaveResponse(this.medicionLoteService.create(medicionLote));
    }
  }

  private createFromForm(): IMedicionLote {
    return {
      ...new MedicionLote(),
      id: this.editForm.get(['id']).value,
      dia: this.editForm.get(['dia']).value,
      tipoMedicion: this.editForm.get(['tipoMedicion']).value,
      estado: this.editForm.get(['estado']).value,
      fechaRealizado:
        this.editForm.get(['fechaRealizado']).value != null
          ? moment(this.editForm.get(['fechaRealizado']).value, DATE_TIME_FORMAT)
          : undefined,
      valor: this.editForm.get(['valor']).value,
      observacion: this.editForm.get(['observacion']).value,
      loteId: this.editForm.get(['loteId']).value,
      tanqueId: this.editForm.get(['tanqueId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMedicionLote>>) {
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

  trackTanqueById(index: number, item: ITanque) {
    return item.id;
  }
}
