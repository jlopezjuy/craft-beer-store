import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IDetalleMovimiento } from 'app/shared/model/detalle-movimiento.model';
import { DetalleMovimientoService } from './detalle-movimiento.service';
import { IPresentacion } from 'app/shared/model/presentacion.model';
import { PresentacionService } from 'app/entities/presentacion';
import { IMovimientos } from 'app/shared/model/movimientos.model';
import { MovimientosService } from 'app/entities/movimientos';

@Component({
    selector: 'jhi-detalle-movimiento-update',
    templateUrl: './detalle-movimiento-update.component.html'
})
export class DetalleMovimientoUpdateComponent implements OnInit {
    detalleMovimiento: IDetalleMovimiento;
    isSaving: boolean;

    presentacions: IPresentacion[];

    movimientos: IMovimientos[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected detalleMovimientoService: DetalleMovimientoService,
        protected presentacionService: PresentacionService,
        protected movimientosService: MovimientosService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ detalleMovimiento }) => {
            this.detalleMovimiento = detalleMovimiento;
        });
        this.presentacionService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IPresentacion[]>) => mayBeOk.ok),
                map((response: HttpResponse<IPresentacion[]>) => response.body)
            )
            .subscribe((res: IPresentacion[]) => (this.presentacions = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.movimientosService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IMovimientos[]>) => mayBeOk.ok),
                map((response: HttpResponse<IMovimientos[]>) => response.body)
            )
            .subscribe((res: IMovimientos[]) => (this.movimientos = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.detalleMovimiento.id !== undefined) {
            this.subscribeToSaveResponse(this.detalleMovimientoService.update(this.detalleMovimiento));
        } else {
            this.subscribeToSaveResponse(this.detalleMovimientoService.create(this.detalleMovimiento));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IDetalleMovimiento>>) {
        result.subscribe((res: HttpResponse<IDetalleMovimiento>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackPresentacionById(index: number, item: IPresentacion) {
        return item.id;
    }

    trackMovimientosById(index: number, item: IMovimientos) {
        return item.id;
    }
}
