import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CraftBeerStoreCajaModule } from './caja/caja.module';
import { CraftBeerStoreEmpresaModule } from './empresa/empresa.module';
import { CraftBeerStoreClienteModule } from './cliente/cliente.module';

@NgModule({
    imports: [CraftBeerStoreCajaModule, CraftBeerStoreEmpresaModule, CraftBeerStoreClienteModule],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CraftBeerStoreEntityModule {}
