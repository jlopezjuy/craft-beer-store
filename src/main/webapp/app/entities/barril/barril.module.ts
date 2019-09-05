import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { CraftBeerStoreSharedModule } from 'app/shared';
import {
  BarrilComponent,
  BarrilDetailComponent,
  BarrilUpdateComponent,
  BarrilDeletePopupComponent,
  BarrilDeleteDialogComponent,
  barrilRoute,
  barrilPopupRoute
} from './';

const ENTITY_STATES = [...barrilRoute, ...barrilPopupRoute];

@NgModule({
  imports: [CraftBeerStoreSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [BarrilComponent, BarrilDetailComponent, BarrilUpdateComponent, BarrilDeleteDialogComponent, BarrilDeletePopupComponent],
  entryComponents: [BarrilComponent, BarrilUpdateComponent, BarrilDeleteDialogComponent, BarrilDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CraftBeerStoreBarrilModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
