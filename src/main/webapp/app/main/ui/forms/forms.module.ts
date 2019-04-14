import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MatButtonModule, MatFormFieldModule, MatIconModule, MatInputModule, MatSelectModule, MatStepperModule } from '@angular/material';

import { FormsComponent } from 'app/main/ui/forms/forms.component';
import { FuseSharedModule } from '../../../../fuse/shared.module';

const routes: Routes = [
    {
        path: 'forms',
        component: FormsComponent
    }
];

@NgModule({
    declarations: [FormsComponent],
    imports: [
        RouterModule.forChild(routes),

        MatButtonModule,
        MatFormFieldModule,
        MatIconModule,
        MatInputModule,
        MatSelectModule,
        MatStepperModule,

        FuseSharedModule
    ]
})
export class UIFormsModule {}
