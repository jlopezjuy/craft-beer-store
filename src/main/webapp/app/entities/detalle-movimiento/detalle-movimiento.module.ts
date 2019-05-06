import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { CraftBeerStoreSharedModule } from 'app/shared';
import {
    DetalleMovimientoComponent,
    DetalleMovimientoDetailComponent,
    DetalleMovimientoUpdateComponent,
    DetalleMovimientoDeletePopupComponent,
    DetalleMovimientoDeleteDialogComponent,
    detalleMovimientoRoute,
    detalleMovimientoPopupRoute
} from './';

const ENTITY_STATES = [...detalleMovimientoRoute, ...detalleMovimientoPopupRoute];

@NgModule({
    imports: [CraftBeerStoreSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        DetalleMovimientoComponent,
        DetalleMovimientoDetailComponent,
        DetalleMovimientoUpdateComponent,
        DetalleMovimientoDeleteDialogComponent,
        DetalleMovimientoDeletePopupComponent
    ],
    entryComponents: [
        DetalleMovimientoComponent,
        DetalleMovimientoUpdateComponent,
        DetalleMovimientoDeleteDialogComponent,
        DetalleMovimientoDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CraftBeerStoreDetalleMovimientoModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
