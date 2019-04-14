import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { MatIconModule } from '@angular/material';

import { Error404Component } from 'app/main/pages/errors/404/error-404.component';
import { FuseSharedModule } from '../../../../../fuse/shared.module';

const routes = [
    {
        path: 'errors/error-404',
        component: Error404Component
    }
];

@NgModule({
    declarations: [Error404Component],
    imports: [RouterModule.forChild(routes), MatIconModule, FuseSharedModule]
})
export class Error404Module {}
