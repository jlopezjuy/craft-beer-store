import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { CraftBeerStoreSharedModule } from 'app/shared';
import {
    RecetaInsumoComponent,
    RecetaInsumoDetailComponent,
    RecetaInsumoUpdateComponent,
    RecetaInsumoDeletePopupComponent,
    RecetaInsumoDeleteDialogComponent,
    recetaInsumoRoute,
    recetaInsumoPopupRoute
} from './';

const ENTITY_STATES = [...recetaInsumoRoute, ...recetaInsumoPopupRoute];

@NgModule({
    imports: [CraftBeerStoreSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        RecetaInsumoComponent,
        RecetaInsumoDetailComponent,
        RecetaInsumoUpdateComponent,
        RecetaInsumoDeleteDialogComponent,
        RecetaInsumoDeletePopupComponent
    ],
    entryComponents: [
        RecetaInsumoComponent,
        RecetaInsumoUpdateComponent,
        RecetaInsumoDeleteDialogComponent,
        RecetaInsumoDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CraftBeerStoreRecetaInsumoModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
