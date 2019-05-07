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
            },
            {
                path: 'producto',
                loadChildren: './producto/producto.module#CraftBeerStoreProductoModule'
            },
            {
                path: 'producto',
                loadChildren: './producto/producto.module#CraftBeerStoreProductoModule'
            },
            {
                path: 'producto',
                loadChildren: './producto/producto.module#CraftBeerStoreProductoModule'
            },
            {
                path: 'proveedor',
                loadChildren: './proveedor/proveedor.module#CraftBeerStoreProveedorModule'
            },
            {
                path: 'presentacion',
                loadChildren: './presentacion/presentacion.module#CraftBeerStorePresentacionModule'
            },
            {
                path: 'cliente',
                loadChildren: './cliente/cliente.module#CraftBeerStoreClienteModule'
            },
            {
                path: 'movimientos',
                loadChildren: './movimientos/movimientos.module#CraftBeerStoreMovimientosModule'
            },
            {
                path: 'detalle-movimiento',
                loadChildren: './detalle-movimiento/detalle-movimiento.module#CraftBeerStoreDetalleMovimientoModule'
            },
            {
                path: 'caja',
                loadChildren: './caja/caja.module#CraftBeerStoreCajaModule'
            },
            {
                path: 'evento',
                loadChildren: './evento/evento.module#CraftBeerStoreEventoModule'
            },
            {
                path: 'evento-producto',
                loadChildren: './evento-producto/evento-producto.module#CraftBeerStoreEventoProductoModule'
            },
            {
                path: 'estilos',
                loadChildren: './estilos/estilos.module#CraftBeerStoreEstilosModule'
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
