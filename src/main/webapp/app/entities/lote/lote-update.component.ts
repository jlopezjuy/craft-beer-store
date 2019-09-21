import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { ILote } from 'app/shared/model/lote.model';
import { LoteService } from './lote.service';
import { IReceta } from 'app/shared/model/receta.model';
import { RecetaService } from 'app/entities/receta';
import { IEmpresa } from 'app/shared/model/empresa.model';
import { EmpresaService } from 'app/entities/empresa';
import { LocalStorageService } from 'ngx-webstorage';
import { DATE_FORMAT } from '../../shared';

@Component({
  selector: 'jhi-lote-update',
  templateUrl: './lote-update.component.html'
})
export class LoteUpdateComponent implements OnInit {
  lote: ILote;
  isSaving: boolean;

  recetas: IReceta[];

  empresas: IEmpresa[];
  empresa: IEmpresa;
  fechaCoccionDp: any;

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected loteService: LoteService,
    protected recetaService: RecetaService,
    protected empresaService: EmpresaService,
    protected activatedRoute: ActivatedRoute,
    private $localStorage: LocalStorageService
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.empresa = this.$localStorage.retrieve('empresa');
    this.activatedRoute.data.subscribe(({ lote }) => {
      this.lote = lote;
      if (this.lote.id) {
        this.fechaCoccionDp = moment(this.lote.fechaCoccion, 'dd/MM/yyy').format();
      }
    });
    this.recetaService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IReceta[]>) => mayBeOk.ok),
        map((response: HttpResponse<IReceta[]>) => response.body)
      )
      .subscribe((res: IReceta[]) => (this.recetas = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.empresaService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IEmpresa[]>) => mayBeOk.ok),
        map((response: HttpResponse<IEmpresa[]>) => response.body)
      )
      .subscribe((res: IEmpresa[]) => (this.empresas = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    this.lote.empresaId = this.empresa.id;
    this.lote.fechaCoccion = this.fechaCoccionDp != null ? moment(this.fechaCoccionDp, DATE_FORMAT) : null;
    if (this.lote.id !== undefined) {
      this.subscribeToSaveResponse(this.loteService.update(this.lote));
    } else {
      this.subscribeToSaveResponse(this.loteService.create(this.lote));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILote>>) {
    result.subscribe((res: HttpResponse<ILote>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackRecetaById(index: number, item: IReceta) {
    return item.id;
  }

  trackEmpresaById(index: number, item: IEmpresa) {
    return item.id;
  }
}