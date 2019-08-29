import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CraftBeerStoreSharedModule } from 'app/shared';
import { HOME_ROUTE, HomeComponent, IngresoEgresoGraphComponent } from './';
import { ChartModule } from 'primeng/chart';
import { MovimientoSemanaGraphComponent } from '../admin/movimiento-semana-graph/movimiento-semana-graph.component';
import { MovimientoProductosSemanaComponent } from '../admin/movimiento-productos-semana/movimiento-productos-semana.component';

@NgModule({
  imports: [CraftBeerStoreSharedModule, RouterModule.forChild([HOME_ROUTE]), ChartModule],
  // declarations: [HomeComponent, IngresoEgresoGraphComponent, MovimientoSemanaGraphComponent, MovimientoProductosSemanaComponent],
  declarations: [HomeComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CraftBeerStoreHomeModule {}
