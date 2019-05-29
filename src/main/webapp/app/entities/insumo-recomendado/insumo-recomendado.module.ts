import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { CraftBeerStoreSharedModule } from 'app/shared';
import {
    InsumoRecomendadoComponent,
    InsumoRecomendadoDetailComponent,
    InsumoRecomendadoUpdateComponent,
    InsumoRecomendadoDeletePopupComponent,
    InsumoRecomendadoDeleteDialogComponent,
    insumoRecomendadoRoute,
    insumoRecomendadoPopupRoute
} from './';

const ENTITY_STATES = [...insumoRecomendadoRoute, ...insumoRecomendadoPopupRoute];

@NgModule({
    imports: [CraftBeerStoreSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        InsumoRecomendadoComponent,
        InsumoRecomendadoDetailComponent,
        InsumoRecomendadoUpdateComponent,
        InsumoRecomendadoDeleteDialogComponent,
        InsumoRecomendadoDeletePopupComponent
    ],
    entryComponents: [
        InsumoRecomendadoComponent,
        InsumoRecomendadoUpdateComponent,
        InsumoRecomendadoDeleteDialogComponent,
        InsumoRecomendadoDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CraftBeerStoreInsumoRecomendadoModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
