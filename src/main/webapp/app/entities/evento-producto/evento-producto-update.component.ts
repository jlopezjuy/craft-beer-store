import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { IEventoProducto, EventoProducto } from 'app/shared/model/evento-producto.model';
import { EventoProductoService } from './evento-producto.service';
import { IProducto } from 'app/shared/model/producto.model';
import { ProductoService } from 'app/entities/producto/producto.service';
import { IEvento } from 'app/shared/model/evento.model';
import { EventoService } from 'app/entities/evento/evento.service';

@Component({
  selector: 'jhi-evento-producto-update',
  templateUrl: './evento-producto-update.component.html'
})
export class EventoProductoUpdateComponent implements OnInit {
  isSaving: boolean;

  productos: IProducto[];

  eventos: IEvento[];

  editForm = this.fb.group({
    id: [],
    cantidadDeBarriles: [],
    productoId: [],
    eventoId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected eventoProductoService: EventoProductoService,
    protected productoService: ProductoService,
    protected eventoService: EventoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ eventoProducto }) => {
      this.updateForm(eventoProducto);
    });
    this.productoService
      .query()
      .subscribe((res: HttpResponse<IProducto[]>) => (this.productos = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.eventoService
      .query()
      .subscribe((res: HttpResponse<IEvento[]>) => (this.eventos = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(eventoProducto: IEventoProducto) {
    this.editForm.patchValue({
      id: eventoProducto.id,
      cantidadDeBarriles: eventoProducto.cantidadDeBarriles,
      productoId: eventoProducto.productoId,
      eventoId: eventoProducto.eventoId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const eventoProducto = this.createFromForm();
    if (eventoProducto.id !== undefined) {
      this.subscribeToSaveResponse(this.eventoProductoService.update(eventoProducto));
    } else {
      this.subscribeToSaveResponse(this.eventoProductoService.create(eventoProducto));
    }
  }

  private createFromForm(): IEventoProducto {
    return {
      ...new EventoProducto(),
      id: this.editForm.get(['id']).value,
      cantidadDeBarriles: this.editForm.get(['cantidadDeBarriles']).value,
      productoId: this.editForm.get(['productoId']).value,
      eventoId: this.editForm.get(['eventoId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEventoProducto>>) {
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

  trackEventoById(index: number, item: IEvento) {
    return item.id;
  }
}
