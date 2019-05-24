import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { CraftBeerStoreSharedModule } from 'app/shared';
import {
    RecetaComponent,
    RecetaDetailComponent,
    RecetaUpdateComponent,
    RecetaDeletePopupComponent,
    RecetaDeleteDialogComponent,
    recetaRoute,
    recetaPopupRoute
} from './';

const ENTITY_STATES = [...recetaRoute, ...recetaPopupRoute];

@NgModule({
    imports: [CraftBeerStoreSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [RecetaComponent, RecetaDetailComponent, RecetaUpdateComponent, RecetaDeleteDialogComponent, RecetaDeletePopupComponent],
    entryComponents: [RecetaComponent, RecetaUpdateComponent, RecetaDeleteDialogComponent, RecetaDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CraftBeerStoreRecetaModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
