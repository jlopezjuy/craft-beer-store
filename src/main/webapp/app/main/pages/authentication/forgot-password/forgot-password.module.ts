import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { MatButtonModule, MatFormFieldModule, MatIconModule, MatInputModule } from '@angular/material';

import { ForgotPasswordComponent } from 'app/main/pages/authentication/forgot-password/forgot-password.component';
import { FuseSharedModule } from '../../../../../fuse/shared.module';

const routes = [
    {
        path: 'auth/forgot-password',
        component: ForgotPasswordComponent
    }
];

@NgModule({
    declarations: [ForgotPasswordComponent],
    imports: [RouterModule.forChild(routes), MatButtonModule, MatFormFieldModule, MatIconModule, MatInputModule, FuseSharedModule]
})
export class ForgotPasswordModule {}
