import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CraftBeerStoreCajaModule } from './caja/caja.module';
import { CraftBeerStoreEmpresaModule } from './empresa/empresa.module';
import { CraftBeerStoreClienteModule } from './cliente/cliente.module';
import { CraftBeerStoreInsumoModule } from './insumo/insumo.module';

@NgModule({
    imports: [CraftBeerStoreCajaModule, CraftBeerStoreEmpresaModule, CraftBeerStoreClienteModule, CraftBeerStoreInsumoModule],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CraftBeerStoreEntityModule {}
