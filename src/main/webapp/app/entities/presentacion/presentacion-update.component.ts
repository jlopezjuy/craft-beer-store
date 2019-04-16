import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IPresentacion } from 'app/shared/model/presentacion.model';
import { PresentacionService } from './presentacion.service';
import { IProducto } from 'app/shared/model/producto.model';
import { ProductoService } from 'app/entities/producto';
import { LocalStorageService } from 'ngx-webstorage';

@Component({
    selector: 'jhi-presentacion-update',
    templateUrl: './presentacion-update.component.html'
})
export class PresentacionUpdateComponent implements OnInit {
    presentacion: IPresentacion;
    isSaving: boolean;

    producto: IProducto;
    fecha: string;

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
            this.fecha = this.presentacion.fecha != null ? this.presentacion.fecha.format(DATE_TIME_FORMAT) : null;
        });
        this.producto = this.$localStorage.retrieve('producto');
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.presentacion.productoId = this.producto.id;
        this.isSaving = true;
        this.presentacion.fecha = this.fecha != null ? moment(this.fecha, DATE_TIME_FORMAT) : null;
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
}
