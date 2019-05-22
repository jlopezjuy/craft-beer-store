import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IPresentacion } from 'app/shared/model/presentacion.model';
import { PresentacionService } from './presentacion.service';
import { IProducto } from 'app/shared/model/producto.model';
import { ProductoService } from 'app/entities/producto';
import { LocalStorageService } from 'ngx-webstorage';
import { DATE_FORMAT, DATE_TIME_FORMAT } from 'app/shared';

@Component({
    selector: 'jhi-presentacion-update',
    templateUrl: './presentacion-update.component.html'
})
export class PresentacionUpdateComponent implements OnInit {
    presentacion: IPresentacion;
    isSaving: boolean;

    producto: IProducto;
    fechaDp: any;
    fecha: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected presentacionService: PresentacionService,
        protected productoService: ProductoService,
        protected activatedRoute: ActivatedRoute,
        private $localStorage: LocalStorageService
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ presentacion }) => {
            this.presentacion = presentacion;
            if (this.presentacion.id) {
                this.fecha = moment(this.presentacion.fecha, 'dd/MM/yyy').format();
            }
        });
        this.producto = this.$localStorage.retrieve('producto');
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.presentacion.productoId = this.producto.id;
        this.isSaving = true;
        this.presentacion.fecha = this.fecha != null ? moment(this.fecha, DATE_FORMAT) : null;
        if (this.presentacion.id !== undefined) {
            this.subscribeToSaveResponse(this.presentacionService.update(this.presentacion));
        } else {
            this.subscribeToSaveResponse(this.presentacionService.create(this.presentacion));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IPresentacion>>) {
        result.subscribe((res: HttpResponse<IPresentacion>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    changeCantidad() {
        if (this.presentacion.cantidad) {
            this.presentacion.precioCostoTotal = this.presentacion.costoUnitario * this.presentacion.cantidad;
        }
        if (this.presentacion.cantidad) {
            this.presentacion.precioVentaTotal = this.presentacion.precioVentaUnitario * this.presentacion.cantidad;
        }
    }

    changePrecioUnitario() {
        if (this.presentacion.cantidad) {
            this.presentacion.precioCostoTotal = this.presentacion.costoUnitario * this.presentacion.cantidad;
        }
    }

    changePrecioVenta() {
        if (this.presentacion.cantidad) {
            this.presentacion.precioVentaTotal = this.presentacion.precioVentaUnitario * this.presentacion.cantidad;
        }
    }
}
