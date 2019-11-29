import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IReceta, Receta } from 'app/shared/model/receta.model';
import { RecetaService } from './receta.service';
import { IProducto } from 'app/shared/model/producto.model';
import { ProductoService } from 'app/entities/producto/producto.service';
import { IInsumo } from 'app/shared/model/insumo.model';
import { InsumoService } from 'app/entities/insumo/insumo.service';

@Component({
  selector: 'jhi-receta-update',
  templateUrl: './receta-update.component.html'
})
export class RecetaUpdateComponent implements OnInit {
  isSaving: boolean;

  productos: IProducto[];

  insumos: IInsumo[];
  fechaDp: any;

  editForm = this.fb.group({
    id: [],
    nombre: [],
    brewMaster: [],
    batch: [],
    temperaturaDeMacerado: [],
    og: [],
    fg: [],
    abv: [],
    ibu: [],
    srm: [],
    empaste: [],
    fecha: [],
    productoId: [],
    maltasId: [],
    lupuloId: [],
    levaduraId: [],
    otrosId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected recetaService: RecetaService,
    protected productoService: ProductoService,
    protected insumoService: InsumoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ receta }) => {
      this.updateForm(receta);
    });
    this.productoService
      .query()
      .subscribe((res: HttpResponse<IProducto[]>) => (this.productos = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.insumoService
      .query()
      .subscribe((res: HttpResponse<IInsumo[]>) => (this.insumos = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(receta: IReceta) {
    this.editForm.patchValue({
      id: receta.id,
      nombre: receta.nombre,
      brewMaster: receta.brewMaster,
      batch: receta.batch,
      temperaturaDeMacerado: receta.temperaturaDeMacerado,
      og: receta.og,
      fg: receta.fg,
      abv: receta.abv,
      ibu: receta.ibu,
      srm: receta.srm,
      empaste: receta.empaste,
      fecha: receta.fecha,
      productoId: receta.productoId,
      maltasId: receta.maltasId,
      lupuloId: receta.lupuloId,
      levaduraId: receta.levaduraId,
      otrosId: receta.otrosId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const receta = this.createFromForm();
    if (receta.id !== undefined) {
      this.subscribeToSaveResponse(this.recetaService.update(receta));
    } else {
      this.subscribeToSaveResponse(this.recetaService.create(receta));
    }
  }

  private createFromForm(): IReceta {
    return {
      ...new Receta(),
      id: this.editForm.get(['id']).value,
      nombre: this.editForm.get(['nombre']).value,
      brewMaster: this.editForm.get(['brewMaster']).value,
      batch: this.editForm.get(['batch']).value,
      temperaturaDeMacerado: this.editForm.get(['temperaturaDeMacerado']).value,
      og: this.editForm.get(['og']).value,
      fg: this.editForm.get(['fg']).value,
      abv: this.editForm.get(['abv']).value,
      ibu: this.editForm.get(['ibu']).value,
      srm: this.editForm.get(['srm']).value,
      empaste: this.editForm.get(['empaste']).value,
      fecha: this.editForm.get(['fecha']).value,
      productoId: this.editForm.get(['productoId']).value,
      maltasId: this.editForm.get(['maltasId']).value,
      lupuloId: this.editForm.get(['lupuloId']).value,
      levaduraId: this.editForm.get(['levaduraId']).value,
      otrosId: this.editForm.get(['otrosId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IReceta>>) {
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

  trackProductoById(index: number, item: IProducto) {
    return item.id;
  }

  trackInsumoById(index: number, item: IInsumo) {
    return item.id;
  }
}
