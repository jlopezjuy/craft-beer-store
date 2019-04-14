import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {
    MatButtonModule,
    MatButtonToggleModule,
    MatIconModule,
    MatListModule,
    MatMenuModule,
    MatSelectModule,
    MatSlideToggleModule,
    MatTabsModule
} from '@angular/material';

import { CardsComponent } from 'app/main/ui/cards/cards.component';
import { NgxChartsModule } from '@swimlane/ngx-charts';
import { FuseSharedModule } from '../../../../fuse/shared.module';
import { FuseDemoModule, FuseHighlightModule } from '../../../../fuse/components';

const routes: Routes = [
    {
        path: 'cards',
        component: CardsComponent
    }
];

@NgModule({
    declarations: [CardsComponent],
    imports: [
        RouterModule.forChild(routes),

        MatButtonModule,
        MatButtonToggleModule,
        MatIconModule,
        MatListModule,
        MatMenuModule,
        MatSelectModule,
        MatSlideToggleModule,
        MatTabsModule,

        NgxChartsModule,

        FuseSharedModule,
        FuseDemoModule,
        FuseHighlightModule
    ]
})
export class UICardsModule {}
