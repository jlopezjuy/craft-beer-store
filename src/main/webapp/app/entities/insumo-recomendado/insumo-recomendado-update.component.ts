import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IInsumoRecomendado } from 'app/shared/model/insumo-recomendado.model';
import { InsumoRecomendadoService } from './insumo-recomendado.service';

@Component({
    selector: 'jhi-insumo-recomendado-update',
    templateUrl: './insumo-recomendado-update.component.html'
})
export class InsumoRecomendadoUpdateComponent implements OnInit {
    insumoRecomendado: IInsumoRecomendado;
    isSaving: boolean;

    constructor(protected insumoRecomendadoService: InsumoRecomendadoService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ insumoRecomendado }) => {
            this.insumoRecomendado = insumoRecomendado;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.insumoRecomendado.id !== undefined) {
            this.subscribeToSaveResponse(this.insumoRecomendadoService.update(this.insumoRecomendado));
        } else {
            this.subscribeToSaveResponse(this.insumoRecomendadoService.create(this.insumoRecomendado));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IInsumoRecomendado>>) {
        result.subscribe((res: HttpResponse<IInsumoRecomendado>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
