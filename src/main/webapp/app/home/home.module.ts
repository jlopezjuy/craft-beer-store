import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CraftBeerStoreSharedModule } from 'app/shared';
import { HOME_ROUTE, HomeComponent, IngresoEgresoGraphComponent } from './';
import { ChartModule } from 'primeng/chart';

@NgModule({
    imports: [CraftBeerStoreSharedModule, RouterModule.forChild([HOME_ROUTE]), ChartModule],
    declarations: [HomeComponent, IngresoEgresoGraphComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CraftBeerStoreHomeModule {}
