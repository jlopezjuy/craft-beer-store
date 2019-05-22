import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ICliente } from 'app/shared/model/cliente.model';
import { ClienteService } from './cliente.service';
import { IEmpresa } from 'app/shared/model/empresa.model';
import { EmpresaService } from 'app/entities/empresa';
import { LocalStorageService } from 'ngx-webstorage';
import { PuntoDeVentaService } from 'app/entities/punto-de-venta';
import { IPuntoDeVenta, PuntoDeVenta } from 'app/shared/model/punto-de-venta.model';
import { MatTableDataSource } from '@angular/material';
import { IProducto } from 'app/shared/model/producto.model';

@Component({
    selector: 'jhi-cliente-update',
    templateUrl: './cliente-update.component.html'
})
export class ClienteUpdateComponent implements OnInit {
    cliente: ICliente;
    isSaving: boolean;
    puntosDeVenta: IPuntoDeVenta[] = [];
    puntoDeVenta: IPuntoDeVenta = new PuntoDeVenta();
    empresa: IEmpresa;
    dataSource: any;
    displayedColumns: string[] = ['nombre', 'direccionDeEntrega', 'localidad', 'notas'];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected clienteService: ClienteService,
        protected empresaService: EmpresaService,
        protected activatedRoute: ActivatedRoute,
        protected router: Router,
        protected $localStorage: LocalStorageService,
        protected puntoDeVentaService: PuntoDeVentaService
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ cliente }) => {
            this.cliente = cliente;
            if (this.cliente.id) {
                this.loadPuntosDeVenta(this.cliente.id);
            }
        });
        this.empresa = this.$localStorage.retrieve('empresa');
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.cliente.empresaId = this.empresa.id;
        this.isSaving = true;
        if (this.cliente.id !== undefined) {
            this.subscribeToSaveResponse(this.clienteService.update(this.cliente));
        } else {
            this.subscribeToSaveResponse(this.clienteService.create(this.cliente));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ICliente>>) {
        result.subscribe((res: HttpResponse<ICliente>) => this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess(cliente: ICliente) {
        this.isSaving = false;
        this.puntosDeVenta.forEach(punto => {
            punto.clienteId = cliente.id;
            if (punto.id) {
                this.puntoDeVentaService.update(punto).subscribe(resp => {
                    console.log('exito');
                });
            } else {
                this.puntoDeVentaService.create(punto).subscribe(reso => {
                    console.log('exito');
                });
            }
        });
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

    protected loadPuntosDeVenta(clienteId: number) {
        this.puntoDeVentaService.queryByCliente(null, clienteId).subscribe(resp => {
            this.puntosDeVenta = resp.body;
            this.dataSource = new MatTableDataSource<IPuntoDeVenta>(this.puntosDeVenta);
        });
    }

    addPuntoDeVenta() {
        this.puntosDeVenta.push(this.puntoDeVenta);
        this.puntoDeVenta = new PuntoDeVenta();
        this.dataSource = new MatTableDataSource<IPuntoDeVenta>(this.puntosDeVenta);
    }
}
