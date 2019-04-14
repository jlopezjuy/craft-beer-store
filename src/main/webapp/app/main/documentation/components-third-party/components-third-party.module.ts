import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { MatButtonModule, MatCheckboxModule, MatIconModule } from '@angular/material';
import { NgxDatatableModule } from '@swimlane/ngx-datatable';

import { GoogleMapsModule } from 'app/main/documentation/components-third-party/google-maps/google-maps.module';
import { DocsComponentsThirdPartyNgxDatatableComponent } from 'app/main/documentation/components-third-party/datatable/ngx-datatable.component';
import { FuseSharedModule } from '../../../../fuse/shared.module';

const routes = [
    {
        path: 'datatables/ngx-datatable',
        component: DocsComponentsThirdPartyNgxDatatableComponent
    }
];

@NgModule({
    declarations: [DocsComponentsThirdPartyNgxDatatableComponent],
    imports: [
        RouterModule.forChild(routes),

        MatButtonModule,
        MatCheckboxModule,
        MatIconModule,

        NgxDatatableModule,

        FuseSharedModule,

        GoogleMapsModule
    ]
})
export class ComponentsThirdPartyModule {}
