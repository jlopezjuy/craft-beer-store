import { NgModule, ApplicationModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IndexComponent } from './index/index.component';
import { routing } from './admin.routing';
import { NgxEchartsModule } from 'ngx-echarts';
import { RichTextEditorAllModule } from '@syncfusion/ej2-angular-richtexteditor';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { FullCalendarModule } from 'ng-fullcalendar';

import { AdminComponent } from './admin/admin.component';
import { DetailTilesComponent } from './detail-tiles/detail-tiles.component';
import { CardActionsComponent } from './card-actions/card-actions.component';
import { TimelinePostComponent } from './timeline-post/timeline-post.component';
import { ActivitiesComponent } from './activities/activities.component';
import { ChatComponent } from './chat/chat.component';
import { GeneralFeedComponent } from './general-feed/general-feed.component';
import { TwitterFeedComponent } from './twitter-feed/twitter-feed.component';
import { MemberInfoComponent } from './member-info/member-info.component';
import { ManagedDataComponent } from './managed-data/managed-data.component';
import { TopProductsComponent } from './top-products/top-products.component';
import { ReferralsComponent } from './referrals/referrals.component';
import { TotalRevenueComponent } from './total-revenue/total-revenue.component';
import { ApplicationsModule } from '../applications/applications.module';
import { ChartsModule } from '../charts/charts.module';
import { FileManagerModule } from '../file-manager/file-manager.module';
import { PagesModule } from '../pages/pages.module';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';
import { CraftBeerStoreSharedModule } from 'app/shared';
/* jhipster-needle-add-admin-module-import - JHipster will add admin modules imports here */
import { CommonElementsModule } from '../common-elements/common-elements.module';
import { TablesModule } from '../tables/tables.module';
import { UiElementsModule } from '../ui-elements/ui-elements.module';
import { LayoutModule } from '../layout/layout.module';
import { FormModule } from '../form/form.module';
import { BlogsModule } from '../blogs/blogs.module';
import { WidgetsModule } from '../widgets/widgets.module';
import { IotDashboardComponent } from './iot-dashboard/iot-dashboard.component';
import { MapsModule } from '../maps/maps.module';

import {
    adminState,
    AuditsComponent,
    UserMgmtComponent,
    UserMgmtDetailComponent,
    UserMgmtUpdateComponent,
    UserMgmtDeleteDialogComponent,
    LogsComponent,
    JhiMetricsMonitoringComponent,
    JhiHealthModalComponent,
    JhiHealthCheckComponent,
    JhiConfigurationComponent,
    JhiDocsComponent,
    JhiTrackerComponent
} from './';
import { CraftBeerStoreEntityModule } from '../entities/entity.module';
@NgModule({
    imports: [
        CraftBeerStoreSharedModule,
        RouterModule.forChild(adminState),
        /* jhipster-needle-add-admin-module - JHipster will add admin modules here */
        CommonModule,
        routing,
        NgxEchartsModule,
        LayoutModule,
        RichTextEditorAllModule,
        NgbModule,
        FullCalendarModule,
        ApplicationsModule,
        ChartsModule,
        FileManagerModule,
        PagesModule,
        RouterModule,
        CommonElementsModule,
        TablesModule,
        UiElementsModule,
        FormModule,
        BlogsModule,
        WidgetsModule,
        MapsModule,
        CraftBeerStoreEntityModule
    ],
    declarations: [
        AuditsComponent,
        UserMgmtComponent,
        UserMgmtDetailComponent,
        UserMgmtUpdateComponent,
        UserMgmtDeleteDialogComponent,
        LogsComponent,
        JhiConfigurationComponent,
        JhiHealthCheckComponent,
        JhiHealthModalComponent,
        JhiDocsComponent,
        JhiTrackerComponent,
        JhiMetricsMonitoringComponent,
        AdminComponent,
        IndexComponent,
        DetailTilesComponent,
        CardActionsComponent,
        TimelinePostComponent,
        ActivitiesComponent,
        ChatComponent,
        GeneralFeedComponent,
        TwitterFeedComponent,
        MemberInfoComponent,
        ManagedDataComponent,
        TopProductsComponent,
        ReferralsComponent,
        TotalRevenueComponent,
        IotDashboardComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }]
})
export class AdminModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
