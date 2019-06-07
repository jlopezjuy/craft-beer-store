import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { ICaja } from 'app/shared/model/caja.model';
import { CajaService } from './caja.service';
import { IProveedor } from 'app/shared/model/proveedor.model';
import { ProveedorService } from 'app/entities/proveedor';
import { ICliente } from 'app/shared/model/cliente.model';
import { ClienteService } from 'app/entities/cliente';
import { IEmpresa } from 'app/shared/model/empresa.model';
import { EmpresaService } from 'app/entities/empresa';
import { LocalStorageService } from 'ngx-webstorage';
import { DATE_FORMAT } from 'app/shared';

@Component({
    selector: 'jhi-caja-update',
    templateUrl: './caja-update.component.html'
})
export class CajaUpdateComponent implements OnInit {
    caja: ICaja;
    isSaving: boolean;
    proveedors: IProveedor[];
    clientes: ICliente[];
    empresa: IEmpresa;
    fechaDp: any;
    fecha: any;

    constructor(
        protected dataUtils: JhiDataUtils,
        protected jhiAlertService: JhiAlertService,
        protected cajaService: CajaService,
        protected proveedorService: ProveedorService,
        protected clienteService: ClienteService,
        protected empresaService: EmpresaService,
        protected activatedRoute: ActivatedRoute,
        protected $localStorage: LocalStorageService
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ caja }) => {
            this.caja = caja;
            if (this.caja.id) {
                this.fecha = moment(this.caja.fecha, 'dd/MM/yyy').format();
            }
        });
        this.empresa = this.$localStorage.retrieve('empresa');
        this.proveedorService
            .queryByEmpresa(null, this.empresa.id)
            .pipe(
                filter((mayBeOk: HttpResponse<IProveedor[]>) => mayBeOk.ok),
                map((response: HttpResponse<IProveedor[]>) => response.body)
            )
            .subscribe((res: IProveedor[]) => (this.proveedors = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.clienteService
            .queryByEmpresa(null, this.empresa.id)
            .pipe(
                filter((mayBeOk: HttpResponse<ICliente[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICliente[]>) => response.body)
            )
            .subscribe((res: ICliente[]) => (this.clientes = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.caja.empresaId = this.empresa.id;
        this.isSaving = true;
        this.caja.fecha = this.fecha != null ? moment(this.fecha, DATE_FORMAT) : null;
        if (this.caja.id !== undefined) {
            this.subscribeToSaveResponse(this.cajaService.update(this.caja));
        } else {
            this.subscribeToSaveResponse(this.cajaService.create(this.caja));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ICaja>>) {
        result.subscribe((res: HttpResponse<ICaja>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackProveedorById(index: number, item: IProveedor) {
        return item.id;
    }

    trackClienteById(index: number, item: ICliente) {
        return item.id;
    }

    trackEmpresaById(index: number, item: IEmpresa) {
        return item.id;
    }
}
