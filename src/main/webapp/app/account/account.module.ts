import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CraftBeerStoreSharedModule } from 'app/shared';

import {
  PasswordStrengthBarComponent,
  RegisterComponent,
  ActivateComponent,
  PasswordComponent,
  PasswordResetInitComponent,
  PasswordResetFinishComponent,
  SettingsComponent,
  accountState
} from './';
import { NgxEchartsModule } from 'ngx-echarts';
import { CraftBeerStoreEmpresaModule } from './settings/empresa/empresa.module';
import {
  EmpresaComponent,
  EmpresaDeleteDialogComponent,
  EmpresaDeletePopupComponent,
  EmpresaDetailComponent,
  EmpresaUpdateComponent
} from './settings/empresa';
import { JhiLanguageService } from 'ng-jhipster';

@NgModule({
  imports: [CraftBeerStoreEmpresaModule, CraftBeerStoreSharedModule, NgxEchartsModule, RouterModule.forChild(accountState)],
  declarations: [
    ActivateComponent,
    RegisterComponent,
    PasswordComponent,
    PasswordStrengthBarComponent,
    PasswordResetInitComponent,
    PasswordResetFinishComponent,
    SettingsComponent,
    EmpresaComponent,
    EmpresaDetailComponent,
    EmpresaUpdateComponent,
    EmpresaDeleteDialogComponent,
    EmpresaDeletePopupComponent
  ],
  entryComponents: [EmpresaComponent, EmpresaUpdateComponent, EmpresaDeleteDialogComponent, EmpresaDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CraftBeerStoreAccountModule {}
