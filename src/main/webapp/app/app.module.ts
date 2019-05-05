import './vendor.ts';

import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { NgbDatepickerConfig } from '@ng-bootstrap/ng-bootstrap';
import { Ng2Webstorage } from 'ngx-webstorage';
import { NgJhipsterModule } from 'ng-jhipster';

import { AuthInterceptor } from './blocks/interceptor/auth.interceptor';
import { AuthExpiredInterceptor } from './blocks/interceptor/auth-expired.interceptor';
import { ErrorHandlerInterceptor } from './blocks/interceptor/errorhandler.interceptor';
import { NotificationInterceptor } from './blocks/interceptor/notification.interceptor';
import { CraftBeerStoreSharedModule } from 'app/shared';
import { CraftBeerStoreCoreModule } from 'app/core';
import { CraftBeerStoreAppRoutingModule } from './app-routing.module';
import { CraftBeerStoreHomeModule } from './home/home.module';
import { CraftBeerStoreAccountModule } from './account/account.module';
import { CraftBeerStoreEntityModule } from './entities/entity.module';
import * as moment from 'moment';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FlexLayoutModule } from '@angular/flex-layout';
import 'hammerjs';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { JhiMainComponent, NavbarComponent, FooterComponent, PageRibbonComponent, ActiveMenuDirective, ErrorComponent } from './layouts';
import { CraftBeerStoreDashboardModule } from 'app/dashboard/dashboard.module';
import { NgxUiLoaderConfig, NgxUiLoaderModule, PB_DIRECTION, POSITION, SPINNER } from 'ngx-ui-loader';
const ngxUiLoaderConfig: NgxUiLoaderConfig = {
    bgsColor: '#OOACC1',
    bgsOpacity: 0.5,
    blur: 8,
    bgsPosition: POSITION.bottomCenter,
    bgsSize: 60,
    bgsType: SPINNER.rectangleBounce,
    fgsColor: '#00ACC1',
    fgsPosition: POSITION.centerCenter,
    fgsSize: 60,
    fgsType: SPINNER.chasingDots,
    logoUrl: '../content/images/icon.svg',
    pbColor: '#77DD77',
    pbDirection: PB_DIRECTION.leftToRight,
    pbThickness: 5,
    // text: '',
    textColor: '#FFFFFF',
    textPosition: POSITION.centerCenter
};
@NgModule({
    imports: [
        BrowserModule,
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
        CraftBeerStoreSharedModule.forRoot(),
        CraftBeerStoreCoreModule,
        CraftBeerStoreHomeModule,
        CraftBeerStoreAccountModule,
        CraftBeerStoreDashboardModule,
        // jhipster-needle-angular-add-module JHipster will add new module here
        CraftBeerStoreEntityModule,
        CraftBeerStoreAppRoutingModule,
        NgxUiLoaderModule.forRoot(ngxUiLoaderConfig)
    ],
    declarations: [JhiMainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, ActiveMenuDirective, FooterComponent],
    // entryComponents: [CraftBeerStoreDashboardModule],
    providers: [
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
        }
    ],
    bootstrap: [JhiMainComponent]
})
export class CraftBeerStoreAppModule {
    constructor(private dpConfig: NgbDatepickerConfig) {
        this.dpConfig.minDate = { year: moment().year() - 100, month: 1, day: 1 };
    }
}
