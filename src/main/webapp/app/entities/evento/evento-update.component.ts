import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IEvento } from 'app/shared/model/evento.model';
import { EventoService } from './evento.service';
import { IEmpresa } from 'app/shared/model/empresa.model';
import { EmpresaService } from 'app/entities/empresa';
import { LocalStorageService } from 'ngx-webstorage';
import { EventoProductoService } from 'app/entities/evento-producto';
import { ProductoService } from 'app/entities/producto';
import { IProducto, Producto } from 'app/shared/model/producto.model';
import { EventoProducto, IEventoProducto } from 'app/shared/model/evento-producto.model';

@Component({
    selector: 'jhi-evento-update',
    templateUrl: './evento-update.component.html'
})
export class EventoUpdateComponent implements OnInit {
    evento: IEvento;
    isSaving: boolean;

    empresa: IEmpresa;
    fechaEventoDp: any;
    productos: IProducto[];
    productosList: IProducto[];
    productoSave: Producto;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected eventoService: EventoService,
        protected empresaService: EmpresaService,
        protected activatedRoute: ActivatedRoute,
        protected $localStorage: LocalStorageService,
        protected eventoProductoService: EventoProductoService,
        protected productoService: ProductoService
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.productosList = [];
        this.activatedRoute.data.subscribe(({ evento }) => {
            this.evento = evento;
            if (this.evento.id) {
                this.loadProductos(this.evento.id);
            }
        });
        this.empresa = this.$localStorage.retrieve('empresa');
        this.productoService
            .queryByEmpresa(null, this.empresa.id)
            .pipe(
                filter((mayBeOk: HttpResponse<IProducto[]>) => mayBeOk.ok),
                map((response: HttpResponse<IProducto[]>) => response.body)
            )
            .subscribe((res: IProducto[]) => (this.productos = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.productoSave = new Producto();
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.evento.empresaId = this.empresa.id;
        if (this.evento.id !== undefined) {
            this.subscribeToSaveResponse(this.eventoService.update(this.evento));
        } else {
            this.subscribeToSaveResponse(this.eventoService.create(this.evento));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IEvento>>) {
        result.subscribe((res: HttpResponse<IEvento>) => this.onSaveSuccess(res), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess(res: HttpResponse<IEvento>) {
        this.isSaving = false;
        this.saveEventoProducto(res.body.id);
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackEmpresaById(index: number, item: IEmpresa) {
        return item.id;
    }

    productoChange(value: number) {
        if (value && value.toString() !== 'null') {
        } else {
            this.productoSave.precioUnitario = null;
            this.productoSave.cantidadPresentacion = null;
        }
    }

    addProducto() {
        if (this.productoSave.id) {
            this.productoService.find(this.productoSave.id).subscribe(resp => {
                this.productosList.push(resp.body);
                console.log(this.productosList);
                this.productoSave.id = null;
            });
        }
    }

    saveEventoProducto(eventoId: number) {
        this.productosList.forEach(prod => {
            const eventoProducto: IEventoProducto = new EventoProducto();
            eventoProducto.eventoId = eventoId;
            eventoProducto.productoId = prod.id;
            eventoProducto.id = prod.eventoId;
            if (eventoProducto.id) {
                this.eventoProductoService.update(eventoProducto).subscribe(resp => {});
            } else {
                this.eventoProductoService.create(eventoProducto).subscribe(resp => {});
            }
        });
    }

    loadProductos(eventoId: number) {
        this.eventoProductoService.queryByEvento(eventoId).subscribe(resp => {
            resp.body.forEach(evento => {
                this.productoService.find(evento.productoId).subscribe(prod => {
                    const producto = prod.body;
                    producto.eventoId = eventoId;
                    this.productosList.push(producto);
                });
            });
        });
    }
}
