import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { MatIconModule } from '@angular/material';
import { MailConfirmComponent } from 'app/main/pages/authentication/mail-confirm/mail-confirm.component';
import { FuseSharedModule } from '../../../../../fuse/shared.module';

const routes = [
    {
        path: 'auth/mail-confirm',
        component: MailConfirmComponent
    }
];

@NgModule({
    declarations: [MailConfirmComponent],
    imports: [RouterModule.forChild(routes), MatIconModule, FuseSharedModule]
})
export class MailConfirmModule {}
