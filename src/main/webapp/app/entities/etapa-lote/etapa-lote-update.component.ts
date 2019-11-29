import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IEtapaLote, EtapaLote } from 'app/shared/model/etapa-lote.model';
import { EtapaLoteService } from './etapa-lote.service';
import { ITanque } from 'app/shared/model/tanque.model';
import { TanqueService } from 'app/entities/tanque/tanque.service';
import { ILote } from 'app/shared/model/lote.model';
import { LoteService } from 'app/entities/lote/lote.service';

@Component({
  selector: 'jhi-etapa-lote-update',
  templateUrl: './etapa-lote-update.component.html'
})
export class EtapaLoteUpdateComponent implements OnInit {
  isSaving: boolean;

  tanques: ITanque[];

  lotes: ILote[];
  inicioDp: any;
  finDp: any;

  editForm = this.fb.group({
    id: [],
    etapa: [],
    litros: [],
    inicio: [],
    fin: [],
    dias: [],
    tanqueId: [],
    loteId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected etapaLoteService: EtapaLoteService,
    protected tanqueService: TanqueService,
    protected loteService: LoteService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ etapaLote }) => {
      this.updateForm(etapaLote);
    });
    this.tanqueService
      .query()
      .subscribe((res: HttpResponse<ITanque[]>) => (this.tanques = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.loteService
      .query()
      .subscribe((res: HttpResponse<ILote[]>) => (this.lotes = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(etapaLote: IEtapaLote) {
    this.editForm.patchValue({
      id: etapaLote.id,
      etapa: etapaLote.etapa,
      litros: etapaLote.litros,
      inicio: etapaLote.inicio,
      fin: etapaLote.fin,
      dias: etapaLote.dias,
      tanqueId: etapaLote.tanqueId,
      loteId: etapaLote.loteId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const etapaLote = this.createFromForm();
    if (etapaLote.id !== undefined) {
      this.subscribeToSaveResponse(this.etapaLoteService.update(etapaLote));
    } else {
      this.subscribeToSaveResponse(this.etapaLoteService.create(etapaLote));
    }
  }

  private createFromForm(): IEtapaLote {
    return {
      ...new EtapaLote(),
      id: this.editForm.get(['id']).value,
      etapa: this.editForm.get(['etapa']).value,
      litros: this.editForm.get(['litros']).value,
      inicio: this.editForm.get(['inicio']).value,
      fin: this.editForm.get(['fin']).value,
      dias: this.editForm.get(['dias']).value,
      tanqueId: this.editForm.get(['tanqueId']).value,
      loteId: this.editForm.get(['loteId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEtapaLote>>) {
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

  trackTanqueById(index: number, item: ITanque) {
    return item.id;
  }

  trackLoteById(index: number, item: ILote) {
    return item.id;
  }
}
