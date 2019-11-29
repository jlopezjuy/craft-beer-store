import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IInsumoRecomendado, InsumoRecomendado } from 'app/shared/model/insumo-recomendado.model';
import { InsumoRecomendadoService } from './insumo-recomendado.service';

@Component({
  selector: 'jhi-insumo-recomendado-update',
  templateUrl: './insumo-recomendado-update.component.html'
})
export class InsumoRecomendadoUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    nombre: [],
    marca: [],
    tipo: []
  });

  constructor(
    protected insumoRecomendadoService: InsumoRecomendadoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ insumoRecomendado }) => {
      this.updateForm(insumoRecomendado);
    });
  }

  updateForm(insumoRecomendado: IInsumoRecomendado) {
    this.editForm.patchValue({
      id: insumoRecomendado.id,
      nombre: insumoRecomendado.nombre,
      marca: insumoRecomendado.marca,
      tipo: insumoRecomendado.tipo
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const insumoRecomendado = this.createFromForm();
    if (insumoRecomendado.id !== undefined) {
      this.subscribeToSaveResponse(this.insumoRecomendadoService.update(insumoRecomendado));
    } else {
      this.subscribeToSaveResponse(this.insumoRecomendadoService.create(insumoRecomendado));
    }
  }

  private createFromForm(): IInsumoRecomendado {
    return {
      ...new InsumoRecomendado(),
      id: this.editForm.get(['id']).value,
      nombre: this.editForm.get(['nombre']).value,
      marca: this.editForm.get(['marca']).value,
      tipo: this.editForm.get(['tipo']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInsumoRecomendado>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
