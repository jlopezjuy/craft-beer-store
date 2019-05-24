import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRecetaInsumo } from 'app/shared/model/receta-insumo.model';

@Component({
    selector: 'jhi-receta-insumo-detail',
    templateUrl: './receta-insumo-detail.component.html'
})
export class RecetaInsumoDetailComponent implements OnInit {
    recetaInsumo: IRecetaInsumo;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ recetaInsumo }) => {
            this.recetaInsumo = recetaInsumo;
        });
    }

    previousState() {
        window.history.back();
    }
}
