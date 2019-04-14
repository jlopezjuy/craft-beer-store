import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MatButtonModule, MatFormFieldModule, MatIconModule, MatInputModule } from '@angular/material';

import { ComingSoonComponent } from 'app/main/pages/coming-soon/coming-soon.component';
import { FuseSharedModule } from '../../../../fuse/shared.module';
import { FuseCountdownModule } from '../../../../fuse/components';

const routes = [
    {
        path: 'coming-soon',
        component: ComingSoonComponent
    }
];

@NgModule({
    declarations: [ComingSoonComponent],
    imports: [
        RouterModule.forChild(routes),

        MatButtonModule,
        MatFormFieldModule,
        MatIconModule,
        MatInputModule,

        FuseSharedModule,
        FuseCountdownModule
    ]
})
export class ComingSoonModule {}
