import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IPuntoDeVenta } from 'app/shared/model/punto-de-venta.model';

@Component({
    selector: 'jhi-punto-de-venta-detail',
    templateUrl: './punto-de-venta-detail.component.html'
})
export class PuntoDeVentaDetailComponent implements OnInit {
    puntoDeVenta: IPuntoDeVenta;

    constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ puntoDeVenta }) => {
            this.puntoDeVenta = puntoDeVenta;
        });
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }
}
