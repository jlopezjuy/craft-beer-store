import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IEstilos, Estilos } from 'app/shared/model/estilos.model';
import { EstilosService } from './estilos.service';

@Component({
  selector: 'jhi-estilos-update',
  templateUrl: './estilos-update.component.html'
})
export class EstilosUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    number: [],
    nombreEstilo: [],
    categoriaEstilo: [],
    abvMin: [],
    abvMax: [],
    abvMed: [],
    ibuMin: [],
    ibuMax: [],
    ibuMed: [],
    srmMin: [],
    srmMax: [],
    srmMed: [],
    descripcion: [],
    ejemploNombreComercial: [],
    ejemploImagenComercial: []
  });

  constructor(protected estilosService: EstilosService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ estilos }) => {
      this.updateForm(estilos);
    });
  }

  updateForm(estilos: IEstilos) {
    this.editForm.patchValue({
      id: estilos.id,
      number: estilos.number,
      nombreEstilo: estilos.nombreEstilo,
      categoriaEstilo: estilos.categoriaEstilo,
      abvMin: estilos.abvMin,
      abvMax: estilos.abvMax,
      abvMed: estilos.abvMed,
      ibuMin: estilos.ibuMin,
      ibuMax: estilos.ibuMax,
      ibuMed: estilos.ibuMed,
      srmMin: estilos.srmMin,
      srmMax: estilos.srmMax,
      srmMed: estilos.srmMed,
      descripcion: estilos.descripcion,
      ejemploNombreComercial: estilos.ejemploNombreComercial,
      ejemploImagenComercial: estilos.ejemploImagenComercial
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const estilos = this.createFromForm();
    if (estilos.id !== undefined) {
      this.subscribeToSaveResponse(this.estilosService.update(estilos));
    } else {
      this.subscribeToSaveResponse(this.estilosService.create(estilos));
    }
  }

  private createFromForm(): IEstilos {
    return {
      ...new Estilos(),
      id: this.editForm.get(['id']).value,
      number: this.editForm.get(['number']).value,
      nombreEstilo: this.editForm.get(['nombreEstilo']).value,
      categoriaEstilo: this.editForm.get(['categoriaEstilo']).value,
      abvMin: this.editForm.get(['abvMin']).value,
      abvMax: this.editForm.get(['abvMax']).value,
      abvMed: this.editForm.get(['abvMed']).value,
      ibuMin: this.editForm.get(['ibuMin']).value,
      ibuMax: this.editForm.get(['ibuMax']).value,
      ibuMed: this.editForm.get(['ibuMed']).value,
      srmMin: this.editForm.get(['srmMin']).value,
      srmMax: this.editForm.get(['srmMax']).value,
      srmMed: this.editForm.get(['srmMed']).value,
      descripcion: this.editForm.get(['descripcion']).value,
      ejemploNombreComercial: this.editForm.get(['ejemploNombreComercial']).value,
      ejemploImagenComercial: this.editForm.get(['ejemploImagenComercial']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEstilos>>) {
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
