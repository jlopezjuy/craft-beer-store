import { Component, OnInit, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IInsumo } from 'app/shared/model/insumo.model';
import { InsumoService } from './insumo.service';
import { IEmpresa } from 'app/shared/model/empresa.model';
import { EmpresaService } from 'app/entities/empresa';
import { LocalStorageService } from 'ngx-webstorage';

@Component({
    selector: 'jhi-insumo-update',
    templateUrl: './insumo-update.component.html'
})
export class InsumoUpdateComponent implements OnInit {
    insumo: IInsumo;
    isSaving: boolean;

    empresa: IEmpresa;

    constructor(
        protected dataUtils: JhiDataUtils,
        protected jhiAlertService: JhiAlertService,
        protected insumoService: InsumoService,
        protected empresaService: EmpresaService,
        protected elementRef: ElementRef,
        protected activatedRoute: ActivatedRoute,
        private $localStorage: LocalStorageService
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ insumo }) => {
            this.insumo = insumo;
        });
        this.empresa = this.$localStorage.retrieve('empresa');
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

    clearInputImage(field: string, fieldContentType: string, idInput: string) {
        this.dataUtils.clearInputImage(this.insumo, this.elementRef, field, fieldContentType, idInput);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.insumo.empresaId = this.empresa.id;
        if (this.insumo.id !== undefined) {
            this.subscribeToSaveResponse(this.insumoService.update(this.insumo));
        } else {
            this.subscribeToSaveResponse(this.insumoService.create(this.insumo));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IInsumo>>) {
        result.subscribe((res: HttpResponse<IInsumo>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
