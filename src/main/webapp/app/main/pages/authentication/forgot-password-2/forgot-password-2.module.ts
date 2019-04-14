import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { MatButtonModule, MatFormFieldModule, MatIconModule, MatInputModule } from '@angular/material';

import { ForgotPassword2Component } from 'app/main/pages/authentication/forgot-password-2/forgot-password-2.component';
import { FuseSharedModule } from '../../../../../fuse/shared.module';

const routes = [
    {
        path: 'auth/forgot-password-2',
        component: ForgotPassword2Component
    }
];

@NgModule({
    declarations: [ForgotPassword2Component],
    imports: [RouterModule.forChild(routes), MatButtonModule, MatFormFieldModule, MatIconModule, MatInputModule, FuseSharedModule]
})
export class ForgotPassword2Module {}
