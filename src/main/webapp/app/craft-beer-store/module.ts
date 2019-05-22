import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CraftBeerStoreSharedModule } from 'app/shared';
import { CRAFT_BEER_STORE_ROUTE, CraftBeerStoreComponent } from './';

@NgModule({
    imports: [CraftBeerStoreSharedModule, RouterModule.forRoot([CRAFT_BEER_STORE_ROUTE], { useHash: true })],
    declarations: [CraftBeerStoreComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CraftBeerStoreCraftBeerStoreModule {}
