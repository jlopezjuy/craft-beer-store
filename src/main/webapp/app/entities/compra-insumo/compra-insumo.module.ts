import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { CraftBeerStoreSharedModule } from 'app/shared';
import {
  CompraInsumoComponent,
  CompraInsumoDetailComponent,
  CompraInsumoUpdateComponent,
  CompraInsumoDeletePopupComponent,
  CompraInsumoDeleteDialogComponent,
  compraInsumoRoute,
  compraInsumoPopupRoute
} from './';

const ENTITY_STATES = [...compraInsumoRoute, ...compraInsumoPopupRoute];

@NgModule({
  imports: [CraftBeerStoreSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    CompraInsumoComponent,
    CompraInsumoDetailComponent,
    CompraInsumoUpdateComponent,
    CompraInsumoDeleteDialogComponent,
    CompraInsumoDeletePopupComponent
  ],
  entryComponents: [
    CompraInsumoComponent,
    CompraInsumoUpdateComponent,
    CompraInsumoDeleteDialogComponent,
    CompraInsumoDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CraftBeerStoreCompraInsumoModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
