import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { MaterialModule } from 'app/main/angular-material-elements/material.module';
import { EXAMPLE_LIST } from 'app/main/angular-material-elements/example-components';
import { AngularMaterialElementsComponent } from 'app/main/angular-material-elements/angular-material-elements.component';
import { ExampleViewerComponent } from 'app/main/angular-material-elements/example-viewer/example-viewer';
import { FuseSharedModule } from '../../../fuse/shared.module';
import { FuseHighlightModule, FuseWidgetModule } from '../../../fuse/components';

const routes: Routes = [
    {
        path: '',
        children: [
            {
                path: ':id',
                component: AngularMaterialElementsComponent
            }
        ]
    }
];

@NgModule({
    declarations: [[...EXAMPLE_LIST], AngularMaterialElementsComponent, ExampleViewerComponent],
    imports: [RouterModule.forChild(routes), MaterialModule, FuseSharedModule, FuseHighlightModule, FuseWidgetModule],
    entryComponents: EXAMPLE_LIST
})
export class AngularMaterialElementsModule {}
