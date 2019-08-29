import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { CraftBeerStoreSharedModule } from 'app/shared';
import {
  CompraInsumoDetalleComponent,
  CompraInsumoDetalleDetailComponent,
  CompraInsumoDetalleUpdateComponent,
  CompraInsumoDetalleDeletePopupComponent,
  CompraInsumoDetalleDeleteDialogComponent,
  compraInsumoDetalleRoute,
  compraInsumoDetallePopupRoute
} from './';

const ENTITY_STATES = [...compraInsumoDetalleRoute, ...compraInsumoDetallePopupRoute];

@NgModule({
  imports: [CraftBeerStoreSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    CompraInsumoDetalleComponent,
    CompraInsumoDetalleDetailComponent,
    CompraInsumoDetalleUpdateComponent,
    CompraInsumoDetalleDeleteDialogComponent,
    CompraInsumoDetalleDeletePopupComponent
  ],
  entryComponents: [
    CompraInsumoDetalleComponent,
    CompraInsumoDetalleUpdateComponent,
    CompraInsumoDetalleDeleteDialogComponent,
    CompraInsumoDetalleDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CraftBeerStoreCompraInsumoDetalleModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
