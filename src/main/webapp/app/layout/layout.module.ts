import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HeaderComponent } from './header/header.component';
import { SidebarComponent } from './sidebar/sidebar.component';
import { PageLoaderComponent } from './page-loader/page-loader.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { RouterModule } from '@angular/router';
import { ActiveMenuDirective } from './header/active-menu.directive';
import { CraftBeerStoreSharedCommonModule } from '../shared';

@NgModule({
    imports: [CommonModule, NgbModule, RouterModule, CraftBeerStoreSharedCommonModule],
    declarations: [HeaderComponent, SidebarComponent, PageLoaderComponent, ActiveMenuDirective],
    exports: [HeaderComponent, SidebarComponent, PageLoaderComponent]
})
export class LayoutModule {}
