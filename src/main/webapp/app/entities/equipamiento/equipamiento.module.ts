import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { CraftBeerStoreSharedModule } from 'app/shared';
import {
    EquipamientoComponent,
    EquipamientoDetailComponent,
    EquipamientoUpdateComponent,
    EquipamientoDeletePopupComponent,
    EquipamientoDeleteDialogComponent,
    equipamientoRoute,
    equipamientoPopupRoute
} from './';

const ENTITY_STATES = [...equipamientoRoute, ...equipamientoPopupRoute];

@NgModule({
    imports: [CraftBeerStoreSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        EquipamientoComponent,
        EquipamientoDetailComponent,
        EquipamientoUpdateComponent,
        EquipamientoDeleteDialogComponent,
        EquipamientoDeletePopupComponent
    ],
    entryComponents: [
        EquipamientoComponent,
        EquipamientoUpdateComponent,
        EquipamientoDeleteDialogComponent,
        EquipamientoDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CraftBeerStoreEquipamientoModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
