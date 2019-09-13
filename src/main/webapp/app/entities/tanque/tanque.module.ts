import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { CraftBeerStoreSharedModule } from 'app/shared';
import {
  TanqueComponent,
  TanqueDetailComponent,
  TanqueUpdateComponent,
  TanqueDeletePopupComponent,
  TanqueDeleteDialogComponent,
  tanqueRoute,
  tanquePopupRoute
} from './';

const ENTITY_STATES = [...tanqueRoute, ...tanquePopupRoute];

@NgModule({
  imports: [CraftBeerStoreSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [TanqueComponent, TanqueDetailComponent, TanqueUpdateComponent, TanqueDeleteDialogComponent, TanqueDeletePopupComponent],
  entryComponents: [TanqueComponent, TanqueUpdateComponent, TanqueDeleteDialogComponent, TanqueDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CraftBeerStoreTanqueModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
