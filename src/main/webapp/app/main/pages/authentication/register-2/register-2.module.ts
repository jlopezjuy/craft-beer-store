import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { MatButtonModule, MatCheckboxModule, MatFormFieldModule, MatIconModule, MatInputModule } from '@angular/material';

import { Register2Component } from 'app/main/pages/authentication/register-2/register-2.component';
import { FuseSharedModule } from '../../../../../fuse/shared.module';

const routes = [
    {
        path: 'auth/register-2',
        component: Register2Component
    }
];

@NgModule({
    declarations: [Register2Component],
    imports: [
        RouterModule.forChild(routes),

        MatButtonModule,
        MatCheckboxModule,
        MatFormFieldModule,
        MatIconModule,
        MatInputModule,

        FuseSharedModule
    ]
})
export class Register2Module {}
