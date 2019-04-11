import { NgModule } from '@angular/core';
import { MatButtonModule, MatIconModule } from '@angular/material';
import { NavbarVerticalStyle1Component } from 'app/components/navbar/vertical/style-1/style-1.component';
import { FuseSharedModule } from '../../../../../@fuse/shared.module';
import { FuseNavigationModule } from '../../../../../@fuse/components';

@NgModule({
    declarations: [NavbarVerticalStyle1Component],
    imports: [MatButtonModule, MatIconModule, FuseSharedModule, FuseNavigationModule],
    exports: [NavbarVerticalStyle1Component]
})
export class NavbarVerticalStyle1Module {}
