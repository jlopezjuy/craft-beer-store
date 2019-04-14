import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MaintenanceComponent } from 'app/main/pages/maintenance/maintenance.component';
import { FuseSharedModule } from '../../../../fuse/shared.module';

const routes = [
    {
        path: 'maintenance',
        component: MaintenanceComponent
    }
];

@NgModule({
    declarations: [MaintenanceComponent],
    imports: [RouterModule.forChild(routes), FuseSharedModule]
})
export class MaintenanceModule {}
