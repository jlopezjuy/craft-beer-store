import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IEstilos } from 'app/shared/model/estilos.model';
import { EstilosService } from './estilos.service';

@Component({
    selector: 'jhi-estilos-update',
    templateUrl: './estilos-update.component.html'
})
export class EstilosUpdateComponent implements OnInit {
    estilos: IEstilos;
    isSaving: boolean;

    constructor(protected estilosService: EstilosService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ estilos }) => {
            this.estilos = estilos;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.estilos.id !== undefined) {
            this.subscribeToSaveResponse(this.estilosService.update(this.estilos));
        } else {
            this.subscribeToSaveResponse(this.estilosService.create(this.estilos));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IEstilos>>) {
        result.subscribe((res: HttpResponse<IEstilos>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
