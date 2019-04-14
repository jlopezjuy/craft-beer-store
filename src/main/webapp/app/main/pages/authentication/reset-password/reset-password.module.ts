import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { MatButtonModule, MatFormFieldModule, MatIconModule, MatInputModule } from '@angular/material';

import { ResetPasswordComponent } from 'app/main/pages/authentication/reset-password/reset-password.component';
import { FuseSharedModule } from '../../../../../fuse/shared.module';

const routes = [
    {
        path: 'auth/reset-password',
        component: ResetPasswordComponent
    }
];

@NgModule({
    declarations: [ResetPasswordComponent],
    imports: [RouterModule.forChild(routes), MatButtonModule, MatFormFieldModule, MatIconModule, MatInputModule, FuseSharedModule]
})
export class ResetPasswordModule {}
