import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {
    MatButtonModule,
    MatDividerModule,
    MatFormFieldModule,
    MatIconModule,
    MatMenuModule,
    MatSelectModule,
    MatTableModule,
    MatTabsModule
} from '@angular/material';
import { NgxChartsModule } from '@swimlane/ngx-charts';

import { ProjectDashboardComponent } from 'app/main/apps/dashboards/project/project.component';
import { ProjectDashboardService } from 'app/main/apps/dashboards/project/project.service';
import { FuseSharedModule } from '../../../../../fuse/shared.module';
import { FuseSidebarModule, FuseWidgetModule } from '../../../../../fuse/components';

const routes: Routes = [
    {
        path: '**',
        component: ProjectDashboardComponent,
        resolve: {
            data: ProjectDashboardService
        }
    }
];

@NgModule({
    declarations: [ProjectDashboardComponent],
    imports: [
        RouterModule.forChild(routes),

        MatButtonModule,
        MatDividerModule,
        MatFormFieldModule,
        MatIconModule,
        MatMenuModule,
        MatSelectModule,
        MatTableModule,
        MatTabsModule,

        NgxChartsModule,

        FuseSharedModule,
        FuseSidebarModule,
        FuseWidgetModule
    ],
    providers: [ProjectDashboardService]
})
export class ProjectDashboardModule {}
