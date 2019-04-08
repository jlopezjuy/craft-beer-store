import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { CraftBeerStoreSharedModule } from 'app/shared';
import {
    InsumoComponent,
    InsumoDetailComponent,
    InsumoUpdateComponent,
    InsumoDeletePopupComponent,
    InsumoDeleteDialogComponent,
    insumoRoute,
    insumoPopupRoute
} from './';

const ENTITY_STATES = [...insumoRoute, ...insumoPopupRoute];

@NgModule({
    imports: [CraftBeerStoreSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [InsumoComponent, InsumoDetailComponent, InsumoUpdateComponent, InsumoDeleteDialogComponent, InsumoDeletePopupComponent],
    entryComponents: [InsumoComponent, InsumoUpdateComponent, InsumoDeleteDialogComponent, InsumoDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CraftBeerStoreInsumoModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
