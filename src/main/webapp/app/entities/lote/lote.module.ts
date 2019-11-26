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
import { LoteDetailDialogComponent } from './lote-detail-dialog.component';
import { LoteEnvasadoComponent } from './lote-envasado/lote-envasado.component';
import { LoteMedicionesComponent } from './lote-mediciones/lote-mediciones.component';
import { NgxEchartsModule } from 'ngx-echarts';
import { LoteEnvasadoDialogComponent } from './lote-envasado/lote-envasado-dialog.component';

const ENTITY_STATES = [...loteRoute, ...lotePopupRoute];

@NgModule({
  imports: [CraftBeerStoreSharedModule, RouterModule.forChild(ENTITY_STATES), NgxEchartsModule],
  declarations: [
    LoteComponent,
    LoteDetailComponent,
    LoteUpdateComponent,
    LoteDeleteDialogComponent,
    LoteDeletePopupComponent,
    LoteDetailDialogComponent,
    LoteEnvasadoComponent,
    LoteMedicionesComponent,
    LoteEnvasadoDialogComponent
  ],
  entryComponents: [
    LoteComponent,
    LoteUpdateComponent,
    LoteDeleteDialogComponent,
    LoteDeletePopupComponent,
    LoteDetailDialogComponent,
    LoteEnvasadoComponent,
    LoteMedicionesComponent,
    LoteEnvasadoDialogComponent
  ],
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
