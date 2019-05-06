import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IEventoProducto } from 'app/shared/model/evento-producto.model';
import { EventoProductoService } from './evento-producto.service';
import { IProducto, Producto } from 'app/shared/model/producto.model';
import { ProductoService } from 'app/entities/producto';
import { IEvento } from 'app/shared/model/evento.model';
import { EventoService } from 'app/entities/evento';

@Component({
    selector: 'jhi-evento-producto-update',
    templateUrl: './evento-producto-update.component.html'
})
export class EventoProductoUpdateComponent implements OnInit {
    eventoProducto: IEventoProducto;
    isSaving: boolean;

    productos: IProducto[];
    productoSave: Producto;
    eventos: IEvento[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected eventoProductoService: EventoProductoService,
        protected productoService: ProductoService,
        protected eventoService: EventoService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ eventoProducto }) => {
            this.eventoProducto = eventoProducto;
        });
        this.productoService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IProducto[]>) => mayBeOk.ok),
                map((response: HttpResponse<IProducto[]>) => response.body)
            )
            .subscribe((res: IProducto[]) => (this.productos = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.eventoService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IEvento[]>) => mayBeOk.ok),
                map((response: HttpResponse<IEvento[]>) => response.body)
            )
            .subscribe((res: IEvento[]) => (this.eventos = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.eventoProducto.id !== undefined) {
            this.subscribeToSaveResponse(this.eventoProductoService.update(this.eventoProducto));
        } else {
            this.subscribeToSaveResponse(this.eventoProductoService.create(this.eventoProducto));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IEventoProducto>>) {
        result.subscribe((res: HttpResponse<IEventoProducto>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
