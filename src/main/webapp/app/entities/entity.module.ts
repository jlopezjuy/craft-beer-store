import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
    imports: [
        RouterModule.forChild([
            {
                path: 'empresa',
                loadChildren: './empresa/empresa.module#CraftBeerStoreEmpresaModule'
            },
            {
                path: 'insumo',
                loadChildren: './insumo/insumo.module#CraftBeerStoreInsumoModule'
            }
            /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
        ])
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CraftBeerStoreEntityModule {}
