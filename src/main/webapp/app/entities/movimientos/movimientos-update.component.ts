import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IMovimientos } from 'app/shared/model/movimientos.model';
import { MovimientosService } from './movimientos.service';
import { ICliente } from 'app/shared/model/cliente.model';
import { ClienteService } from 'app/entities/cliente';
import { IEmpresa } from 'app/shared/model/empresa.model';
import { EmpresaService } from 'app/entities/empresa';
import { LocalStorageService } from 'ngx-webstorage';
import { IPresentacion, Presentacion } from 'app/shared/model/presentacion.model';
import { ProductoService } from 'app/entities/producto';
import { IProducto, Producto } from 'app/shared/model/producto.model';
import { PresentacionService } from 'app/entities/presentacion';
import { DetalleMovimientoService } from 'app/entities/detalle-movimiento';
import { DetalleMovimiento } from 'app/shared/model/detalle-movimiento.model';
import { NgxUiLoaderService } from 'ngx-ui-loader';

@Component({
    selector: 'jhi-movimientos-update',
    templateUrl: './movimientos-update.component.html'
})
export class MovimientosUpdateComponent implements OnInit {
    movimientos: IMovimientos;
    isSaving: boolean;

    clientes: ICliente[];

    empresa: IEmpresa;
    fechaMovimientoDp: any;
    presentacions: IPresentacion[] = [];
    presentacionesAdd: IPresentacion[] = [];
    productoId: number;
    productos: IProducto[];
    productoSave: Producto;
    isEditable: boolean;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected movimientosService: MovimientosService,
        protected clienteService: ClienteService,
        protected empresaService: EmpresaService,
        protected activatedRoute: ActivatedRoute,
        protected productoService: ProductoService,
        protected presentacionService: PresentacionService,
        protected detalleMovimientoService: DetalleMovimientoService,
        private $localStorage: LocalStorageService,
        private ngxService: NgxUiLoaderService
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ movimientos }) => {
            this.movimientos = movimientos;
            this.movimientos.precioTotal = this.movimientos.precioTotal === undefined ? 0 : this.movimientos.precioTotal;
            this.isEditable = this.movimientos.id === undefined ? true : false;
            if (this.movimientos.id !== undefined) {
                this.loadAllOnEdit();
            }
        });
        this.empresa = this.$localStorage.retrieve('empresa');
        this.clienteService
            .queryByEmpresa(null, this.empresa.id)
            .pipe(
                filter((mayBeOk: HttpResponse<ICliente[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICliente[]>) => response.body)
            )
            .subscribe((res: ICliente[]) => (this.clientes = res), (res: HttpErrorResponse) => this.onError(res.message));

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
        this.movimientos.empresaId = this.empresa.id;
        this.isSaving = true;
        if (this.movimientos.id !== undefined) {
            this.subscribeToSaveResponse(this.movimientosService.update(this.movimientos));
        } else {
            this.subscribeToSaveResponse(this.movimientosService.create(this.movimientos));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IMovimientos>>) {
        result.subscribe((res: HttpResponse<IMovimientos>) => this.onSaveSuccess(res), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess(res: HttpResponse<IMovimientos>) {
        this.isSaving = false;
        const movimiento: IMovimientos = res.body;
        const movimientoId = movimiento.id;
        this.presentacions.forEach(pres => {
            const detalle: DetalleMovimiento = new DetalleMovimiento();
            detalle.movimientosId = movimientoId;
            detalle.presentacionId = pres.id;
            detalle.cantidad = pres.cantidad;
            detalle.precioTotal = pres.precioTotal;
            this.detalleMovimientoService.create(detalle).subscribe(det => {});
        });
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackClienteById(index: number, item: ICliente) {
        return item.id;
    }

    trackEmpresaById(index: number, item: IEmpresa) {
        return item.id;
    }

    trackProductoById(index: number, item: IProducto) {
        return item.id;
    }

    productoChange(value: number) {
        if (value && value.toString() !== 'null') {
            this.presentacionService.queryByProducto(null, value).subscribe(resp => {
                this.presentacionesAdd = resp.body;
            });
        } else {
            this.presentacionesAdd = [];
            this.productoSave.precioUnitario = null;
            this.productoSave.cantidadPresentacion = null;
        }
    }

    presentacionChange(value: any) {
        this.presentacionService.find(value).subscribe(response => {
            this.productoSave.precioUnitario = response.body.precioVentaUnitario;
        });
    }

    addPresentacion() {
        this.presentacionService.find(this.productoSave.presentacionId).subscribe(resp => {
            this.productoService.find(resp.body.productoId).subscribe(prod => {
                const pres = resp.body;
                pres.cantidad = this.productoSave.cantidadPresentacion;
                pres.nombreComercial = prod.body.nombreComercial;
                pres.precioTotal = pres.cantidad * pres.precioVentaUnitario;
                pres.movimientoId = resp.body.movimientoId;
                this.presentacions.push(pres);
                this.movimientos.precioTotal = this.movimientos.precioTotal + pres.precioTotal;
                this.clearFormProduct();
            });
        });
    }

    clearFormProduct() {
        this.productoSave.id = null;
        this.productoSave.presentacionId = null;
        this.productoSave.cantidadPresentacion = 0;
        this.productoSave.precioUnitario = null;
    }

    saveMovimiento() {
        this.save();
    }

    loadAllOnEdit() {
        this.detalleMovimientoService.queryByMovimiento(null, this.movimientos.id).subscribe(detalle => {
            detalle.body.forEach(det => {
                const presentacion: IPresentacion = new Presentacion();
                presentacion.cantidad = det.cantidad;
                presentacion.precioTotal = det.precioTotal;
                this.presentacionService.find(det.presentacionId).subscribe(presen => {
                    presentacion.tipoPresentacion = presen.body.tipoPresentacion;
                    this.productoService.find(presen.body.productoId).subscribe(prod => {
                        presentacion.nombreComercial = prod.body.nombreComercial;
                    });
                });
                this.presentacions.push(presentacion);
            });
        });
    }
}
