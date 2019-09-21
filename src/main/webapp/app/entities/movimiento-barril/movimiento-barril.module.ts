import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { CraftBeerStoreSharedModule } from 'app/shared';
import {
  MovimientoBarrilComponent,
  MovimientoBarrilDetailComponent,
  MovimientoBarrilUpdateComponent,
  MovimientoBarrilDeletePopupComponent,
  MovimientoBarrilDeleteDialogComponent,
  movimientoBarrilRoute,
  movimientoBarrilPopupRoute
} from './';

const ENTITY_STATES = [...movimientoBarrilRoute, ...movimientoBarrilPopupRoute];

@NgModule({
  imports: [CraftBeerStoreSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    MovimientoBarrilComponent,
    MovimientoBarrilDetailComponent,
    MovimientoBarrilUpdateComponent,
    MovimientoBarrilDeleteDialogComponent,
    MovimientoBarrilDeletePopupComponent
  ],
  entryComponents: [
    MovimientoBarrilComponent,
    MovimientoBarrilUpdateComponent,
    MovimientoBarrilDeleteDialogComponent,
    MovimientoBarrilDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CraftBeerStoreMovimientoBarrilModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
