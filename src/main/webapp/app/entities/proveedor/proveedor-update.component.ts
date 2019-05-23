import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IProveedor } from 'app/shared/model/proveedor.model';
import { ProveedorService } from './proveedor.service';
import { IEmpresa } from 'app/shared/model/empresa.model';
import { EmpresaService } from 'app/entities/empresa';
import { LocalStorageService } from 'ngx-webstorage';
import { DATE_FORMAT } from 'app/shared';

@Component({
    selector: 'jhi-proveedor-update',
    templateUrl: './proveedor-update.component.html'
})
export class ProveedorUpdateComponent implements OnInit {
    proveedor: IProveedor;
    isSaving: boolean;

    phonemask = ['(', /\d/, /\d/, /\d/, ')', /\d/, /\d/, /\d/, /\d/, /\d/, /\d/, /\d/];

    empresa: IEmpresa;
    fechaAltaDp: any;

    constructor(
        protected dataUtils: JhiDataUtils,
        protected jhiAlertService: JhiAlertService,
        protected proveedorService: ProveedorService,
        protected empresaService: EmpresaService,
        protected activatedRoute: ActivatedRoute,
        private $localStorage: LocalStorageService
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ proveedor }) => {
            this.proveedor = proveedor;
            if (this.proveedor.id) {
                this.fechaAltaDp = moment(this.proveedor.fechaAlta, 'dd/MM/yyy').format();
            } else {
                this.fechaAltaDp = moment(moment(), 'dd/MM/yyy').format();
            }
        });
        this.empresa = this.$localStorage.retrieve('empresa');
    }

    clearFormBeforeSave() {
        if (this.proveedor.correo.length < 1) {
            console.log(this.proveedor.correo);
            this.proveedor.correo = null;
        }
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
        this.clearFormBeforeSave();
        this.proveedor.empresaId = this.empresa.id;
        this.proveedor.fechaAlta = this.fechaAltaDp != null ? moment(this.fechaAltaDp, DATE_FORMAT) : null;
        this.isSaving = true;
        if (this.proveedor.id !== undefined) {
            this.subscribeToSaveResponse(this.proveedorService.update(this.proveedor));
        } else {
            this.subscribeToSaveResponse(this.proveedorService.create(this.proveedor));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IProveedor>>) {
        result.subscribe((res: HttpResponse<IProveedor>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackEmpresaById(index: number, item: IEmpresa) {
        return item.id;
    }
}
