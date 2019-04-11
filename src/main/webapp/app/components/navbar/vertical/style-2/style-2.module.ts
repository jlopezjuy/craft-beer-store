import { NgModule } from '@angular/core';
import { MatButtonModule, MatIconModule } from '@angular/material';
import { NavbarVerticalStyle2Component } from 'app/components/navbar/vertical/style-2/style-2.component';
import { FuseSharedModule } from '../../../../../@fuse/shared.module';
import { FuseNavigationModule } from '../../../../../@fuse/components';

@NgModule({
    declarations: [NavbarVerticalStyle2Component],
    imports: [MatButtonModule, MatIconModule, FuseSharedModule, FuseNavigationModule],
    exports: [NavbarVerticalStyle2Component]
})
export class NavbarVerticalStyle2Module {}
