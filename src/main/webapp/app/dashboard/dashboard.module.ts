import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CraftBeerStoreBarchartModule } from './barchart/barchart.module';
import { CraftBeerStoreDoughnutchartModule } from './doughnutchart/doughnutchart.module';
import { CraftBeerStoreLinechartModule } from './linechart/linechart.module';
import { CraftBeerStorePiechartModule } from './piechart/piechart.module';
import { CraftBeerStorePolarareachartModule } from './polarareachart/polarareachart.module';
import { CraftBeerStoreRadarchartModule } from './radarchart/radarchart.module';

@NgModule({
    imports: [
        CraftBeerStoreBarchartModule,
        CraftBeerStoreDoughnutchartModule,
        CraftBeerStoreLinechartModule,
        CraftBeerStorePiechartModule,
        CraftBeerStorePolarareachartModule,
        CraftBeerStoreRadarchartModule
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    exports: [CraftBeerStoreBarchartModule, CraftBeerStoreDoughnutchartModule, CraftBeerStoreLinechartModule],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CraftBeerStoreDashboardModule {}
