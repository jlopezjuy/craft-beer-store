import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IEtapaLote } from 'app/shared/model/etapa-lote.model';
import { EtapaLoteService } from './etapa-lote.service';
import { ITanque } from 'app/shared/model/tanque.model';
import { TanqueService } from 'app/entities/tanque';
import { ILote } from 'app/shared/model/lote.model';
import { LoteService } from 'app/entities/lote';

@Component({
  selector: 'jhi-etapa-lote-update',
  templateUrl: './etapa-lote-update.component.html'
})
export class EtapaLoteUpdateComponent implements OnInit {
  etapaLote: IEtapaLote;
  isSaving: boolean;

  tanques: ITanque[];

  lotes: ILote[];
  inicioDp: any;
  finDp: any;

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected etapaLoteService: EtapaLoteService,
    protected tanqueService: TanqueService,
    protected loteService: LoteService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ etapaLote }) => {
      this.etapaLote = etapaLote;
    });
    this.tanqueService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ITanque[]>) => mayBeOk.ok),
        map((response: HttpResponse<ITanque[]>) => response.body)
      )
      .subscribe((res: ITanque[]) => (this.tanques = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.loteService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ILote[]>) => mayBeOk.ok),
        map((response: HttpResponse<ILote[]>) => response.body)
      )
      .subscribe((res: ILote[]) => (this.lotes = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    if (this.etapaLote.id !== undefined) {
      this.subscribeToSaveResponse(this.etapaLoteService.update(this.etapaLote));
    } else {
      this.subscribeToSaveResponse(this.etapaLoteService.create(this.etapaLote));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEtapaLote>>) {
    result.subscribe((res: HttpResponse<IEtapaLote>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
