import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { CraftBeerStoreSharedModule } from 'app/shared';
import {
  MedicionLoteComponent,
  MedicionLoteDetailComponent,
  MedicionLoteUpdateComponent,
  MedicionLoteDeletePopupComponent,
  MedicionLoteDeleteDialogComponent,
  medicionLoteRoute,
  medicionLotePopupRoute
} from './';

const ENTITY_STATES = [...medicionLoteRoute, ...medicionLotePopupRoute];

@NgModule({
  imports: [CraftBeerStoreSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    MedicionLoteComponent,
    MedicionLoteDetailComponent,
    MedicionLoteUpdateComponent,
    MedicionLoteDeleteDialogComponent,
    MedicionLoteDeletePopupComponent
  ],
  entryComponents: [
    MedicionLoteComponent,
    MedicionLoteUpdateComponent,
    MedicionLoteDeleteDialogComponent,
    MedicionLoteDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CraftBeerStoreMedicionLoteModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
