import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IReceta } from 'app/shared/model/receta.model';
import { RecetaService } from './receta.service';
import { IProducto } from 'app/shared/model/producto.model';
import { ProductoService } from 'app/entities/producto';
import { LocalStorageService } from 'ngx-webstorage';
import { NgxUiLoaderService } from 'ngx-ui-loader';
import { DATE_FORMAT } from 'app/shared';
import { InsumoService } from 'app/entities/insumo';
import { IEmpresa } from 'app/shared/model/empresa.model';
import { IInsumo, TipoInsumo } from 'app/shared/model/insumo.model';

@Component({
    selector: 'jhi-receta-update',
    templateUrl: './receta-update.component.html'
})
export class RecetaUpdateComponent implements OnInit {
    receta: IReceta;
    isSaving: boolean;
    fechaDp: any;
    producto: IProducto;
    maxDate = new Date();
    empresa: IEmpresa;
    maltas: IInsumo[];
    lupulos: IInsumo[];
    levaduras: IInsumo[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected recetaService: RecetaService,
        protected productoService: ProductoService,
        protected activatedRoute: ActivatedRoute,
        protected insumoService: InsumoService,
        private $localStorage: LocalStorageService,
        private ngxLoader: NgxUiLoaderService
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ receta }) => {
            this.receta = receta;
            if (this.receta.id) {
                this.fechaDp = moment(this.receta.fecha, 'dd/MM/yyy').format();
            }
        });
        this.producto = this.$localStorage.retrieve('producto');
        this.empresa = this.$localStorage.retrieve('empresa');
        console.log(this.empresa);
        this.insumoService.queryByEmpresaTipo(this.empresa.id, TipoInsumo.MALTA).subscribe(resp => {
            this.maltas = resp.body;
        });
        this.insumoService.queryByEmpresaTipo(this.empresa.id, TipoInsumo.LUPULO).subscribe(resp => {
            this.lupulos = resp.body;
        });
        this.insumoService.queryByEmpresaTipo(this.empresa.id, TipoInsumo.LEVADURA).subscribe(resp => {
            this.levaduras = resp.body;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.ngxLoader.start();
        this.isSaving = true;
        this.receta.productoId = this.producto.id;
        this.receta.fecha = this.fechaDp != null ? moment(this.fechaDp, DATE_FORMAT) : null;
        if (this.receta.id !== undefined) {
            this.subscribeToSaveResponse(this.recetaService.update(this.receta));
        } else {
            this.subscribeToSaveResponse(this.recetaService.create(this.receta));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IReceta>>) {
        result.subscribe((res: HttpResponse<IReceta>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.ngxLoader.stop();
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

    addMalta() {}
}
