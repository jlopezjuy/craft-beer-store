import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CraftBeerStoreSharedModule } from 'app/shared';
import { MANUAL_USUARIO_ROUTE, ManualUsuarioComponent } from './';

@NgModule({
    imports: [CraftBeerStoreSharedModule, RouterModule.forRoot([MANUAL_USUARIO_ROUTE], { useHash: true })],
    declarations: [ManualUsuarioComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CraftBeerStoreManualUsuarioModule {}
