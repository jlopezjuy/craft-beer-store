import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { CraftBeerStoreSharedModule } from 'app/shared';
import {
  PresentacionComponent,
  PresentacionDetailComponent,
  PresentacionUpdateComponent,
  PresentacionDeletePopupComponent,
  PresentacionDeleteDialogComponent,
  presentacionRoute,
  presentacionPopupRoute
} from './';

const ENTITY_STATES = [...presentacionRoute, ...presentacionPopupRoute];

@NgModule({
  imports: [CraftBeerStoreSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    PresentacionComponent,
    PresentacionDetailComponent,
    PresentacionUpdateComponent,
    PresentacionDeleteDialogComponent,
    PresentacionDeletePopupComponent
  ],
  entryComponents: [
    PresentacionComponent,
    PresentacionUpdateComponent,
    PresentacionDeleteDialogComponent,
    PresentacionDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CraftBeerStorePresentacionModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
