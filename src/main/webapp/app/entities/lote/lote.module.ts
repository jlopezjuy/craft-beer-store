import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { CraftBeerStoreSharedModule } from 'app/shared';
import {
  LoteComponent,
  LoteDetailComponent,
  LoteUpdateComponent,
  LoteDeletePopupComponent,
  LoteDeleteDialogComponent,
  loteRoute,
  lotePopupRoute
} from './';

const ENTITY_STATES = [...loteRoute, ...lotePopupRoute];

@NgModule({
  imports: [CraftBeerStoreSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [LoteComponent, LoteDetailComponent, LoteUpdateComponent, LoteDeleteDialogComponent, LoteDeletePopupComponent],
  entryComponents: [LoteComponent, LoteUpdateComponent, LoteDeleteDialogComponent, LoteDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CraftBeerStoreLoteModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}