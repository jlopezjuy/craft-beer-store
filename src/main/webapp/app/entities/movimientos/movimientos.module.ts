import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { CraftBeerStoreSharedModule } from 'app/shared';
import {
    MovimientosComponent,
    MovimientosDetailComponent,
    MovimientosUpdateComponent,
    MovimientosDeletePopupComponent,
    MovimientosDeleteDialogComponent,
    movimientosRoute,
    movimientosPopupRoute
} from './';

const ENTITY_STATES = [...movimientosRoute, ...movimientosPopupRoute];

@NgModule({
    imports: [CraftBeerStoreSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        MovimientosComponent,
        MovimientosDetailComponent,
        MovimientosUpdateComponent,
        MovimientosDeleteDialogComponent,
        MovimientosDeletePopupComponent
    ],
    entryComponents: [MovimientosComponent, MovimientosUpdateComponent, MovimientosDeleteDialogComponent, MovimientosDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CraftBeerStoreMovimientosModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
