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
import { IPresentacion } from 'app/shared/model/presentacion.model';
import { ProductoService } from 'app/entities/producto';
import { IProducto, Producto } from 'app/shared/model/producto.model';
import { PresentacionService } from 'app/entities/presentacion';

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

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected movimientosService: MovimientosService,
        protected clienteService: ClienteService,
        protected empresaService: EmpresaService,
        protected activatedRoute: ActivatedRoute,
        protected productoService: ProductoService,
        protected presentacionService: PresentacionService,
        private $localStorage: LocalStorageService
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ movimientos }) => {
            this.movimientos = movimientos;
        });
        this.empresa = this.$localStorage.retrieve('empresa');
        this.clienteService
            .query(null, this.empresa.id)
            .pipe(
                filter((mayBeOk: HttpResponse<ICliente[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICliente[]>) => response.body)
            )
            .subscribe((res: ICliente[]) => (this.clientes = res), (res: HttpErrorResponse) => this.onError(res.message));

        this.productoService
            .query(null, this.empresa.id)
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
        result.subscribe((res: HttpResponse<IMovimientos>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackClienteById(index: number, item: ICliente) {
        return item.id;
    }

    trackEmpresaById(index: number, item: IEmpresa) {
        return item.id;
    }

    productoChange(value: any) {
        console.dir(value.toString());
        this.presentacionService.query(null, value).subscribe(resp => {
            console.log(resp);
            this.presentacionesAdd = resp.body;
        });
    }

    presentacionChange(value: any) {
        console.log(value);
    }

    addPresentacion() {
        console.log(this.productoSave);
        this.presentacionService.find(this.productoSave.presentacionId).subscribe(resp => {
            this.productoService.find(resp.body.productoId).subscribe(prod => {
                console.log(resp);
                const pres = resp.body;
                console.log(pres);
                pres.cantidad = this.productoSave.cantidadPresentacion;
                pres.nombreComercial = prod.body.nombreComercial;
                pres.precioTotal = pres.cantidad * pres.precioVentaUnitario;
                this.presentacions.push(pres);
            });
        });
    }
}
