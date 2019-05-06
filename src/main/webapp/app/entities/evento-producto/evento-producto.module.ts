import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { CraftBeerStoreSharedModule } from 'app/shared';
import {
    EventoProductoComponent,
    EventoProductoDetailComponent,
    EventoProductoUpdateComponent,
    EventoProductoDeletePopupComponent,
    EventoProductoDeleteDialogComponent,
    eventoProductoRoute,
    eventoProductoPopupRoute
} from './';

const ENTITY_STATES = [...eventoProductoRoute, ...eventoProductoPopupRoute];

@NgModule({
    imports: [CraftBeerStoreSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        EventoProductoComponent,
        EventoProductoDetailComponent,
        EventoProductoUpdateComponent,
        EventoProductoDeleteDialogComponent,
        EventoProductoDeletePopupComponent
    ],
    entryComponents: [
        EventoProductoComponent,
        EventoProductoUpdateComponent,
        EventoProductoDeleteDialogComponent,
        EventoProductoDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CraftBeerStoreEventoProductoModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
