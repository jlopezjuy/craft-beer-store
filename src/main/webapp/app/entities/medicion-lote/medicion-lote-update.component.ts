import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IMedicionLote } from 'app/shared/model/medicion-lote.model';
import { MedicionLoteService } from './medicion-lote.service';
import { ILote } from 'app/shared/model/lote.model';
import { LoteService } from 'app/entities/lote';
import { ITanque } from 'app/shared/model/tanque.model';
import { TanqueService } from 'app/entities/tanque';

@Component({
  selector: 'jhi-medicion-lote-update',
  templateUrl: './medicion-lote-update.component.html'
})
export class MedicionLoteUpdateComponent implements OnInit {
  medicionLote: IMedicionLote;
  isSaving: boolean;

  lotes: ILote[];

  tanques: ITanque[];
  fechaRealizado: string;

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected medicionLoteService: MedicionLoteService,
    protected loteService: LoteService,
    protected tanqueService: TanqueService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ medicionLote }) => {
      this.medicionLote = medicionLote;
      this.fechaRealizado = this.medicionLote.fechaRealizado != null ? this.medicionLote.fechaRealizado.format(DATE_TIME_FORMAT) : null;
    });
    this.loteService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ILote[]>) => mayBeOk.ok),
        map((response: HttpResponse<ILote[]>) => response.body)
      )
      .subscribe((res: ILote[]) => (this.lotes = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.tanqueService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ITanque[]>) => mayBeOk.ok),
        map((response: HttpResponse<ITanque[]>) => response.body)
      )
      .subscribe((res: ITanque[]) => (this.tanques = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    this.medicionLote.fechaRealizado = this.fechaRealizado != null ? moment(this.fechaRealizado, DATE_TIME_FORMAT) : null;
    if (this.medicionLote.id !== undefined) {
      this.subscribeToSaveResponse(this.medicionLoteService.update(this.medicionLote));
    } else {
      this.subscribeToSaveResponse(this.medicionLoteService.create(this.medicionLote));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMedicionLote>>) {
    result.subscribe((res: HttpResponse<IMedicionLote>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
