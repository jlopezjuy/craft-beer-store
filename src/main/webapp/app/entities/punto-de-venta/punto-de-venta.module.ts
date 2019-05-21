import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { CraftBeerStoreSharedModule } from 'app/shared';
import {
    PuntoDeVentaComponent,
    PuntoDeVentaDetailComponent,
    PuntoDeVentaUpdateComponent,
    PuntoDeVentaDeletePopupComponent,
    PuntoDeVentaDeleteDialogComponent,
    puntoDeVentaRoute,
    puntoDeVentaPopupRoute
} from './';

const ENTITY_STATES = [...puntoDeVentaRoute, ...puntoDeVentaPopupRoute];

@NgModule({
    imports: [CraftBeerStoreSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        PuntoDeVentaComponent,
        PuntoDeVentaDetailComponent,
        PuntoDeVentaUpdateComponent,
        PuntoDeVentaDeleteDialogComponent,
        PuntoDeVentaDeletePopupComponent
    ],
    entryComponents: [
        PuntoDeVentaComponent,
        PuntoDeVentaUpdateComponent,
        PuntoDeVentaDeleteDialogComponent,
        PuntoDeVentaDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CraftBeerStorePuntoDeVentaModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
