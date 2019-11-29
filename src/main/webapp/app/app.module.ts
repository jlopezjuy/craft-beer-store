import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { CraftBeerStoreSharedModule } from 'app/shared/shared.module';
import { CraftBeerStoreCoreModule } from 'app/core/core.module';
import { CraftBeerStoreAppRoutingModule } from './app-routing.module';
import { CraftBeerStoreHomeModule } from './home/home.module';
import { CraftBeerStoreEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { JhiMainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ActiveMenuDirective } from './layouts/navbar/active-menu.directive';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    CraftBeerStoreSharedModule,
    CraftBeerStoreCoreModule,
    CraftBeerStoreHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    CraftBeerStoreEntityModule,
    CraftBeerStoreAppRoutingModule
  ],
  declarations: [JhiMainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, ActiveMenuDirective, FooterComponent],
  bootstrap: [JhiMainComponent]
})
export class CraftBeerStoreAppModule {}
