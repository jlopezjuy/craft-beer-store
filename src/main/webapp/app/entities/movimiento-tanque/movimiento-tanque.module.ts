import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { CraftBeerStoreSharedModule } from 'app/shared';
import {
  MovimientoTanqueComponent,
  MovimientoTanqueDetailComponent,
  MovimientoTanqueUpdateComponent,
  MovimientoTanqueDeletePopupComponent,
  MovimientoTanqueDeleteDialogComponent,
  movimientoTanqueRoute,
  movimientoTanquePopupRoute
} from './';

const ENTITY_STATES = [...movimientoTanqueRoute, ...movimientoTanquePopupRoute];

@NgModule({
  imports: [CraftBeerStoreSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    MovimientoTanqueComponent,
    MovimientoTanqueDetailComponent,
    MovimientoTanqueUpdateComponent,
    MovimientoTanqueDeleteDialogComponent,
    MovimientoTanqueDeletePopupComponent
  ],
  entryComponents: [
    MovimientoTanqueComponent,
    MovimientoTanqueUpdateComponent,
    MovimientoTanqueDeleteDialogComponent,
    MovimientoTanqueDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CraftBeerStoreMovimientoTanqueModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
