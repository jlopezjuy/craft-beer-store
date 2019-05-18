import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService, JhiLanguageService } from 'ng-jhipster';
import { IEvento } from 'app/shared/model/evento.model';
import { EventoService } from './evento.service';
import { IEmpresa } from 'app/shared/model/empresa.model';
import { EmpresaService } from 'app/entities/empresa';
import { LocalStorageService } from 'ngx-webstorage';
import { EventoProductoService } from 'app/entities/evento-producto';
import { ProductoService } from 'app/entities/producto';
import { IProducto, Producto } from 'app/shared/model/producto.model';
import { EventoProducto, IEventoProducto } from 'app/shared/model/evento-producto.model';
import { MatSnackBar } from '@angular/material';
import { Message } from 'primeng/components/common/api';
import { MessageService } from 'primeng/components/common/messageservice';
import { TranslateService } from '@ngx-translate/core';
import { DATE_FORMAT } from 'app/shared';

@Component({
    selector: 'jhi-evento-update',
    templateUrl: './evento-update.component.html',
    providers: [MessageService]
})
export class EventoUpdateComponent implements OnInit {
    evento: IEvento;
    isSaving: boolean;

    empresa: IEmpresa;
    fechaEventoDp: any;
    productos: IProducto[];
    productosList: IProducto[];
    productoSave: Producto;
    msgs: Message[] = [];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected eventoService: EventoService,
        protected empresaService: EmpresaService,
        protected activatedRoute: ActivatedRoute,
        protected $localStorage: LocalStorageService,
        protected eventoProductoService: EventoProductoService,
        protected productoService: ProductoService,
        private snackBar: MatSnackBar,
        private languageService: JhiLanguageService,
        private translateService: TranslateService
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.productosList = [];
        this.activatedRoute.data.subscribe(({ evento }) => {
            this.evento = evento;
            if (this.evento.id) {
                this.loadProductos(this.evento.id);
                this.fechaEventoDp = moment(this.evento.fechaEvento, 'dd/MM/yyy').format();
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
        this.evento.fechaEvento = this.fechaEventoDp != null ? moment(this.fechaEventoDp, DATE_FORMAT) : null;
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
                if (this.validateProductoList(resp.body)) {
                    this.productosList.push(resp.body);
                } else {
                    this.jhiAlertService.warning('craftBeerStoreApp.evento.validate.producto');
                    this.translateService.get('craftBeerStoreApp.evento.validate.producto').subscribe(mess => {
                        this.openSnackBar(mess, '');
                    });
                }
                this.productoSave.id = null;
            });
        }
    }

    validateProductoList(producto: IProducto) {
        let validate = true;
        this.productosList.forEach(prod => {
            if (prod.id === producto.id) {
                validate = false;
            }
        });
        return validate;
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

    openSnackBar(message: string, action: string) {
        this.snackBar.open(message, action, {
            duration: 2000
        });
    }
}
