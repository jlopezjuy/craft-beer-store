import { Component, OnInit, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IProducto } from 'app/shared/model/producto.model';
import { ProductoService } from './producto.service';
import { IEmpresa } from 'app/shared/model/empresa.model';
import { EmpresaService } from 'app/entities/empresa';
import { LocalStorageService } from 'ngx-webstorage';
import { IEstilos } from 'app/shared/model/estilos.model';
import { EstilosService } from 'app/entities/estilos';

@Component({
    selector: 'jhi-producto-update',
    templateUrl: './producto-update.component.html'
})
export class ProductoUpdateComponent implements OnInit {
    producto: IProducto;
    isSaving: boolean;

    empresa: IEmpresa;

    estilos: IEstilos[];

    constructor(
        protected dataUtils: JhiDataUtils,
        protected jhiAlertService: JhiAlertService,
        protected productoService: ProductoService,
        protected empresaService: EmpresaService,
        protected estilosService: EstilosService,
        protected elementRef: ElementRef,
        protected activatedRoute: ActivatedRoute,
        private $localStorage: LocalStorageService
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ producto }) => {
            this.producto = producto;
        });
        this.empresa = this.$localStorage.retrieve('empresa');
        this.estilosService
            .queryAll()
            .pipe(
                filter((mayBeOk: HttpResponse<IEstilos[]>) => mayBeOk.ok),
                map((response: HttpResponse<IEstilos[]>) => response.body)
            )
            .subscribe((res: IEstilos[]) => (this.estilos = res), (res: HttpErrorResponse) => this.onError(res.message));
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
        this.dataUtils.clearInputImage(this.producto, this.elementRef, field, fieldContentType, idInput);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.producto.empresaId = this.empresa.id;
        this.isSaving = true;
        if (this.producto.id !== undefined) {
            this.subscribeToSaveResponse(this.productoService.update(this.producto));
        } else {
            this.subscribeToSaveResponse(this.productoService.create(this.producto));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IProducto>>) {
        result.subscribe((res: HttpResponse<IProducto>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackEstilosById(index: number, item: IEstilos) {
        return item.id;
    }
}
