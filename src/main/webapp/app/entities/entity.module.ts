import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CraftBeerStoreCajaModule } from './caja/caja.module';
import { CraftBeerStoreClienteModule } from './cliente/cliente.module';
import { CraftBeerStoreInsumoModule } from './insumo/insumo.module';
import { CraftBeerStoreProductoModule } from './producto/producto.module';
import { CraftBeerStoreProveedorModule } from './proveedor/proveedor.module';
import { CraftBeerStoreMovimientosModule } from './movimientos/movimientos.module';
import { CraftBeerStoreEventoModule } from './evento/evento.module';
import { CraftBeerStoreEstilosModule } from './estilos/estilos.module';
import { CraftBeerStorePuntoDeVentaModule } from './punto-de-venta/punto-de-venta.module';
import { CraftBeerStorePresentacionModule } from './presentacion/presentacion.module';
import { CraftBeerStoreRecetaModule } from './receta/receta.module';

@NgModule({
  imports: [
    CraftBeerStoreCajaModule,
    CraftBeerStoreClienteModule,
    CraftBeerStoreInsumoModule,
    CraftBeerStoreProductoModule,
    CraftBeerStoreProveedorModule,
    CraftBeerStoreMovimientosModule,
    CraftBeerStoreEventoModule,
    CraftBeerStoreEstilosModule,
    CraftBeerStorePuntoDeVentaModule,
    CraftBeerStorePresentacionModule,
    CraftBeerStoreRecetaModule
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CraftBeerStoreEntityModule {}
