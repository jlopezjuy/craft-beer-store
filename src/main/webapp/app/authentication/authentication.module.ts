import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PageLoginComponent } from './page-login/page-login.component';
import { AuthenticationComponent } from './authentication/authentication.component';
import { routing } from './authentication.routing';
import { PageRegisterComponent } from './page-register/page-register.component';
import { PageLockscreenComponent } from './page-lockscreen/page-lockscreen.component';
import { PageForgotPasswordComponent } from './page-forgot-password/page-forgot-password.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { PageForbiddonErrorComponent } from './page-forbiddon-error/page-forbiddon-error.component';
import { PageIsErrorComponent } from './page-is-error/page-is-error.component';
import { PageTryLaterComponent } from './page-try-later/page-try-later.component';
import { PagesModule } from '../pages/pages.module';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { JhiLanguageService } from 'ng-jhipster';
import { CraftBeerStoreSharedModule } from '../shared';
import { PasswordStrengthBarComponent } from './passwordRegister/password-strength-bar.component';
import { JhiLanguageHelper } from '../core';
import { PasswordForgotPasswordFinishComponent } from './page-forgot-password-finish/password-forgot-password-finish.component';
import { ActivateAccountComponent } from './activate-account/activate-account.component';

// import {PasswordResetFinishComponent, PasswordResetInitComponent, PasswordStrengthBarComponent} from "../account";

@NgModule({
  declarations: [
    PageLoginComponent,
    AuthenticationComponent,
    PageRegisterComponent,
    PageLockscreenComponent,
    PageForgotPasswordComponent,
    PasswordForgotPasswordFinishComponent,
    ActivateAccountComponent,
    PageNotFoundComponent,
    PageForbiddonErrorComponent,
    PageIsErrorComponent,
    PageTryLaterComponent,
    PasswordStrengthBarComponent
  ],
  imports: [CommonModule, routing, PagesModule, RouterModule, FormsModule, CraftBeerStoreSharedModule],
  entryComponents: [PageLoginComponent],
  exports: [PageLoginComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuthenticationModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
