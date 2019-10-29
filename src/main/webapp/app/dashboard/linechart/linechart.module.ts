import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CraftBeerStoreSharedModule } from '../../shared';

import { LinechartComponent, linechartRoute } from './';

const DASHBOARD_STATES = [linechartRoute];

@NgModule({
  imports: [CraftBeerStoreSharedModule, RouterModule.forRoot(DASHBOARD_STATES, { useHash: true })],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CraftBeerStoreLinechartModule {}
