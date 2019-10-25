import './vendor.ts';

import { BrowserModule } from '@angular/platform-browser';
import { Injectable, NgModule } from '@angular/core';
import { NgbActiveModal, NgbDatepickerConfig, NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { AppComponent } from './app.component';
import { routing } from './app.routing';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ToastrModule } from 'ngx-toastr';
import { RichTextEditorAllModule } from '@syncfusion/ej2-angular-richtexteditor';
import { FullCalendarModule } from 'ng-fullcalendar';
import { NgMultiSelectDropDownModule } from 'ng-multiselect-dropdown';
import { LeafletModule } from '@asymmetrik/ngx-leaflet';
import { NgxGalleryModule } from 'ngx-gallery';
import 'hammerjs';
import * as moment from 'moment';
import 'moment-timezone';

import * as $ from 'jquery';
import { NgxUiLoaderConfig, NgxUiLoaderModule } from 'ngx-ui-loader';
import { AuthInterceptor } from './blocks/interceptor/auth.interceptor';
import { HTTP_INTERCEPTORS, HttpClient } from '@angular/common/http';
import { AuthExpiredInterceptor } from './blocks/interceptor/auth-expired.interceptor';
import { ErrorHandlerInterceptor } from './blocks/interceptor/errorhandler.interceptor';
import { NotificationInterceptor } from './blocks/interceptor/notification.interceptor';
import { LoaderInterceptor } from './blocks/interceptor/loader.interceptor';
import { FlexLayoutModule } from '@angular/flex-layout';
import { Ng2Webstorage } from 'ngx-webstorage';
import { CraftBeerStoreSharedModule } from './shared';
import { CraftBeerStoreCoreModule } from './core';
import { CraftBeerStoreDashboardModule } from './dashboard/dashboard.module';
import { CraftBeerStoreAccountModule } from './account/account.module';
import { NgJhipsterModule } from 'ng-jhipster';

const ngxUiLoaderConfig: NgxUiLoaderConfig = {
  bgsColor: '#00ACC1',
  bgsOpacity: 0.5,
  bgsPosition: 'bottom-right',
  bgsSize: 60,
  bgsType: 'ball-spin-clockwise',
  blur: 9,
  fgsColor: '#00ACC1',
  fgsPosition: 'center-center',
  fgsSize: 60,
  fgsType: 'ball-spin-clockwise',
  gap: 24,
  logoPosition: 'center-center',
  logoSize: 120,
  logoUrl: '../content/images/icon.svg',
  masterLoaderId: 'master',
  overlayBorderRadius: '0',
  overlayColor: 'rgba(40, 40, 40, 0.8)',
  pbColor: '#77DD77',
  pbDirection: 'ltr',
  pbThickness: 3,
  hasProgressBar: true,
  text: '',
  textColor: '#FFFFFF',
  textPosition: 'center-center',
  threshold: 500
};

@NgModule({
  imports: [
    BrowserModule,
    routing,
    NgbModule,
    BrowserAnimationsModule,
    FlexLayoutModule,
    Ng2Webstorage.forRoot({ prefix: 'jhi', separator: '-' }),
    NgJhipsterModule.forRoot({
      // set below to true to make alerts look like toast
      alertAsToast: false,
      alertTimeout: 5000,
      i18nEnabled: true,
      defaultI18nLang: 'es'
    }),
    ToastrModule.forRoot(),
    RichTextEditorAllModule,
    FullCalendarModule,
    NgMultiSelectDropDownModule.forRoot(),
    LeafletModule.forRoot(),
    NgxGalleryModule,
    CraftBeerStoreSharedModule.forRoot(),
    CraftBeerStoreCoreModule,
    // CraftBeerStoreAccountModule,
    CraftBeerStoreDashboardModule,
    // CraftBeerStoreCraftBeerStoreModule,
    // CraftBeerStoreEntityModule,
    NgxUiLoaderModule.forRoot(ngxUiLoaderConfig)
  ],
  declarations: [AppComponent],
  providers: [
    NgbActiveModal,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthExpiredInterceptor,
      multi: true
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: ErrorHandlerInterceptor,
      multi: true
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: NotificationInterceptor,
      multi: true
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: LoaderInterceptor,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
  constructor(private dpConfig: NgbDatepickerConfig) {
    this.dpConfig.minDate = { year: moment().year() - 100, month: 1, day: 1 };
  }
}
