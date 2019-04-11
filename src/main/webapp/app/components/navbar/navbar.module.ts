import { NgModule } from '@angular/core';
import { NavbarComponent } from 'app/components/navbar/navbar.component';
import { FuseSharedModule } from '../../../@fuse/shared.module';
import { NavbarHorizontalStyle1Module } from 'app/components/navbar/horizontal/style-1/style-1.module';
import { NavbarVerticalStyle1Module } from 'app/components/navbar/vertical/style-1/style-1.module';
import { NavbarVerticalStyle2Module } from 'app/components/navbar/vertical/style-2/style-2.module';

@NgModule({
    declarations: [NavbarComponent],
    imports: [FuseSharedModule, NavbarHorizontalStyle1Module, NavbarVerticalStyle1Module, NavbarVerticalStyle2Module],
    exports: [NavbarComponent]
})
export class NavbarModule {}
