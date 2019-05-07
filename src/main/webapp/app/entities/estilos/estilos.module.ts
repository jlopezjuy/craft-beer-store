import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { CraftBeerStoreSharedModule } from 'app/shared';
import {
    EstilosComponent,
    EstilosDetailComponent,
    EstilosUpdateComponent,
    EstilosDeletePopupComponent,
    EstilosDeleteDialogComponent,
    estilosRoute,
    estilosPopupRoute
} from './';

const ENTITY_STATES = [...estilosRoute, ...estilosPopupRoute];

@NgModule({
    imports: [CraftBeerStoreSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        EstilosComponent,
        EstilosDetailComponent,
        EstilosUpdateComponent,
        EstilosDeleteDialogComponent,
        EstilosDeletePopupComponent
    ],
    entryComponents: [EstilosComponent, EstilosUpdateComponent, EstilosDeleteDialogComponent, EstilosDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CraftBeerStoreEstilosModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
