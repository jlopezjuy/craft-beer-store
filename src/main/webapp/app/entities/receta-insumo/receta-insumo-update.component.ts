import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { IRecetaInsumo, RecetaInsumo } from 'app/shared/model/receta-insumo.model';
import { RecetaInsumoService } from './receta-insumo.service';
import { IInsumoRecomendado } from 'app/shared/model/insumo-recomendado.model';
import { InsumoRecomendadoService } from 'app/entities/insumo-recomendado/insumo-recomendado.service';
import { IReceta } from 'app/shared/model/receta.model';
import { RecetaService } from 'app/entities/receta/receta.service';

@Component({
  selector: 'jhi-receta-insumo-update',
  templateUrl: './receta-insumo-update.component.html'
})
export class RecetaInsumoUpdateComponent implements OnInit {
  isSaving: boolean;

  insumorecomendados: IInsumoRecomendado[];

  recetas: IReceta[];

  editForm = this.fb.group({
    id: [],
    tipoInsumo: [],
    cantidad: [],
    color: [null, [Validators.min(2), Validators.max(40)]],
    porcentaje: [null, [Validators.max(100)]],
    usoMalta: [],
    alpha: [null, [Validators.max(100)]],
    modoLupulo: [],
    gramos: [],
    usoLupulo: [],
    tiempo: [null, [Validators.min(0), Validators.max(120)]],
    ibu: [],
    densidadLeva: [],
    tamSobre: [],
    atenuacion: [],
    tipoOtro: [],
    usoOtro: [],
    tiempoOtro: [],
    insumoRecomendadoId: [],
    recetaId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected recetaInsumoService: RecetaInsumoService,
    protected insumoRecomendadoService: InsumoRecomendadoService,
    protected recetaService: RecetaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ recetaInsumo }) => {
      this.updateForm(recetaInsumo);
    });
    this.insumoRecomendadoService
      .query()
      .subscribe(
        (res: HttpResponse<IInsumoRecomendado[]>) => (this.insumorecomendados = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.recetaService
      .query()
      .subscribe((res: HttpResponse<IReceta[]>) => (this.recetas = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(recetaInsumo: IRecetaInsumo) {
    this.editForm.patchValue({
      id: recetaInsumo.id,
      tipoInsumo: recetaInsumo.tipoInsumo,
      cantidad: recetaInsumo.cantidad,
      color: recetaInsumo.color,
      porcentaje: recetaInsumo.porcentaje,
      usoMalta: recetaInsumo.usoMalta,
      alpha: recetaInsumo.alpha,
      modoLupulo: recetaInsumo.modoLupulo,
      gramos: recetaInsumo.gramos,
      usoLupulo: recetaInsumo.usoLupulo,
      tiempo: recetaInsumo.tiempo,
      ibu: recetaInsumo.ibu,
      densidadLeva: recetaInsumo.densidadLeva,
      tamSobre: recetaInsumo.tamSobre,
      atenuacion: recetaInsumo.atenuacion,
      tipoOtro: recetaInsumo.tipoOtro,
      usoOtro: recetaInsumo.usoOtro,
      tiempoOtro: recetaInsumo.tiempoOtro,
      insumoRecomendadoId: recetaInsumo.insumoRecomendadoId,
      recetaId: recetaInsumo.recetaId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const recetaInsumo = this.createFromForm();
    if (recetaInsumo.id !== undefined) {
      this.subscribeToSaveResponse(this.recetaInsumoService.update(recetaInsumo));
    } else {
      this.subscribeToSaveResponse(this.recetaInsumoService.create(recetaInsumo));
    }
  }

  private createFromForm(): IRecetaInsumo {
    return {
      ...new RecetaInsumo(),
      id: this.editForm.get(['id']).value,
      tipoInsumo: this.editForm.get(['tipoInsumo']).value,
      cantidad: this.editForm.get(['cantidad']).value,
      color: this.editForm.get(['color']).value,
      porcentaje: this.editForm.get(['porcentaje']).value,
      usoMalta: this.editForm.get(['usoMalta']).value,
      alpha: this.editForm.get(['alpha']).value,
      modoLupulo: this.editForm.get(['modoLupulo']).value,
      gramos: this.editForm.get(['gramos']).value,
      usoLupulo: this.editForm.get(['usoLupulo']).value,
      tiempo: this.editForm.get(['tiempo']).value,
      ibu: this.editForm.get(['ibu']).value,
      densidadLeva: this.editForm.get(['densidadLeva']).value,
      tamSobre: this.editForm.get(['tamSobre']).value,
      atenuacion: this.editForm.get(['atenuacion']).value,
      tipoOtro: this.editForm.get(['tipoOtro']).value,
      usoOtro: this.editForm.get(['usoOtro']).value,
      tiempoOtro: this.editForm.get(['tiempoOtro']).value,
      insumoRecomendadoId: this.editForm.get(['insumoRecomendadoId']).value,
      recetaId: this.editForm.get(['recetaId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRecetaInsumo>>) {
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

  trackInsumoRecomendadoById(index: number, item: IInsumoRecomendado) {
    return item.id;
  }

  trackRecetaById(index: number, item: IReceta) {
    return item.id;
  }
}
