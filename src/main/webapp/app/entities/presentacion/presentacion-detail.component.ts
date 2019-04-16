import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPresentacion } from 'app/shared/model/presentacion.model';

@Component({
    selector: 'jhi-presentacion-detail',
    templateUrl: './presentacion-detail.component.html'
})
export class PresentacionDetailComponent implements OnInit {
    presentacion: IPresentacion;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ presentacion }) => {
            this.presentacion = presentacion;
        });
    }

    previousState() {
        window.history.back();
    }
}
