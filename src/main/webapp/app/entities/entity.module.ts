import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CraftBeerStoreCajaModule } from './caja/caja.module';
import { CraftBeerStoreEmpresaModule } from './empresa/empresa.module';
import { CraftBeerStoreClienteModule } from './cliente/cliente.module';
import { CraftBeerStoreInsumoModule } from './insumo/insumo.module';
import { CraftBeerStoreProductoModule } from './producto/producto.module';
import { CraftBeerStoreProveedorModule } from './proveedor/proveedor.module';
import { CraftBeerStoreMovimientosModule } from './movimientos/movimientos.module';
import { CraftBeerStoreEventoModule } from './evento/evento.module';
import { CraftBeerStoreEstilosModule } from './estilos/estilos.module';
import { CraftBeerStoreEquipamientoModule } from './equipamiento/equipamiento.module';
import { CraftBeerStorePuntoDeVentaModule } from './punto-de-venta/punto-de-venta.module';
import { CraftBeerStorePresentacionModule } from './presentacion/presentacion.module';
import { CraftBeerStoreRecetaModule } from './receta/receta.module';
import { CraftBeerStoreCompraInsumoModule } from './compra-insumo/compra-insumo.module';

@NgModule({
  imports: [
    CraftBeerStoreCajaModule,
    CraftBeerStoreEmpresaModule,
    CraftBeerStoreClienteModule,
    CraftBeerStoreInsumoModule,
    CraftBeerStoreProductoModule,
    CraftBeerStoreProveedorModule,
    CraftBeerStoreMovimientosModule,
    CraftBeerStoreEventoModule,
    CraftBeerStoreEstilosModule,
    CraftBeerStoreEquipamientoModule,
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
