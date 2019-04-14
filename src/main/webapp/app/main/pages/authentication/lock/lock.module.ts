import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { MatButtonModule, MatFormFieldModule, MatIconModule, MatInputModule } from '@angular/material';

import { LockComponent } from 'app/main/pages/authentication/lock/lock.component';
import { FuseSharedModule } from '../../../../../fuse/shared.module';

const routes = [
    {
        path: 'auth/lock',
        component: LockComponent
    }
];

@NgModule({
    declarations: [LockComponent],
    imports: [RouterModule.forChild(routes), MatButtonModule, MatFormFieldModule, MatIconModule, MatInputModule, FuseSharedModule]
})
export class LockModule {}
