import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IRecetaInsumo } from 'app/shared/model/receta-insumo.model';
import { RecetaInsumoService } from './receta-insumo.service';
import { IInsumo } from 'app/shared/model/insumo.model';
import { InsumoService } from 'app/entities/insumo';
import { IReceta } from 'app/shared/model/receta.model';
import { RecetaService } from 'app/entities/receta';

@Component({
    selector: 'jhi-receta-insumo-update',
    templateUrl: './receta-insumo-update.component.html'
})
export class RecetaInsumoUpdateComponent implements OnInit {
    recetaInsumo: IRecetaInsumo;
    isSaving: boolean;

    insumos: IInsumo[];

    recetas: IReceta[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected recetaInsumoService: RecetaInsumoService,
        protected insumoService: InsumoService,
        protected recetaService: RecetaService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ recetaInsumo }) => {
            this.recetaInsumo = recetaInsumo;
        });
        this.insumoService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IInsumo[]>) => mayBeOk.ok),
                map((response: HttpResponse<IInsumo[]>) => response.body)
            )
            .subscribe((res: IInsumo[]) => (this.insumos = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.recetaService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IReceta[]>) => mayBeOk.ok),
                map((response: HttpResponse<IReceta[]>) => response.body)
            )
            .subscribe((res: IReceta[]) => (this.recetas = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.recetaInsumo.id !== undefined) {
            this.subscribeToSaveResponse(this.recetaInsumoService.update(this.recetaInsumo));
        } else {
            this.subscribeToSaveResponse(this.recetaInsumoService.create(this.recetaInsumo));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IRecetaInsumo>>) {
        result.subscribe((res: HttpResponse<IRecetaInsumo>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackInsumoById(index: number, item: IInsumo) {
        return item.id;
    }

    trackRecetaById(index: number, item: IReceta) {
        return item.id;
    }
}
