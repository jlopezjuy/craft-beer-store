import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'empresa',
        loadChildren: () => import('./empresa/empresa.module').then(m => m.CraftBeerStoreEmpresaModule)
      },
      {
        path: 'insumo',
        loadChildren: () => import('./insumo/insumo.module').then(m => m.CraftBeerStoreInsumoModule)
      },
      {
        path: 'producto',
        loadChildren: () => import('./producto/producto.module').then(m => m.CraftBeerStoreProductoModule)
      },
      {
        path: 'proveedor',
        loadChildren: () => import('./proveedor/proveedor.module').then(m => m.CraftBeerStoreProveedorModule)
      },
      {
        path: 'presentacion',
        loadChildren: () => import('./presentacion/presentacion.module').then(m => m.CraftBeerStorePresentacionModule)
      },
      {
        path: 'cliente',
        loadChildren: () => import('./cliente/cliente.module').then(m => m.CraftBeerStoreClienteModule)
      },
      {
        path: 'movimientos',
        loadChildren: () => import('./movimientos/movimientos.module').then(m => m.CraftBeerStoreMovimientosModule)
      },
      {
        path: 'detalle-movimiento',
        loadChildren: () => import('./detalle-movimiento/detalle-movimiento.module').then(m => m.CraftBeerStoreDetalleMovimientoModule)
      },
      {
        path: 'caja',
        loadChildren: () => import('./caja/caja.module').then(m => m.CraftBeerStoreCajaModule)
      },
      {
        path: 'evento',
        loadChildren: () => import('./evento/evento.module').then(m => m.CraftBeerStoreEventoModule)
      },
      {
        path: 'evento-producto',
        loadChildren: () => import('./evento-producto/evento-producto.module').then(m => m.CraftBeerStoreEventoProductoModule)
      },
      {
        path: 'estilos',
        loadChildren: () => import('./estilos/estilos.module').then(m => m.CraftBeerStoreEstilosModule)
      },
      {
        path: 'equipamiento',
        loadChildren: () => import('./equipamiento/equipamiento.module').then(m => m.CraftBeerStoreEquipamientoModule)
      },
      {
        path: 'punto-de-venta',
        loadChildren: () => import('./punto-de-venta/punto-de-venta.module').then(m => m.CraftBeerStorePuntoDeVentaModule)
      },
      {
        path: 'receta',
        loadChildren: () => import('./receta/receta.module').then(m => m.CraftBeerStoreRecetaModule)
      },
      {
        path: 'receta-insumo',
        loadChildren: () => import('./receta-insumo/receta-insumo.module').then(m => m.CraftBeerStoreRecetaInsumoModule)
      },
      {
        path: 'insumo-recomendado',
        loadChildren: () => import('./insumo-recomendado/insumo-recomendado.module').then(m => m.CraftBeerStoreInsumoRecomendadoModule)
      },
      {
        path: 'compra-insumo',
        loadChildren: () => import('./compra-insumo/compra-insumo.module').then(m => m.CraftBeerStoreCompraInsumoModule)
      },
      {
        path: 'compra-insumo-detalle',
        loadChildren: () =>
          import('./compra-insumo-detalle/compra-insumo-detalle.module').then(m => m.CraftBeerStoreCompraInsumoDetalleModule)
      },
      {
        path: 'barril',
        loadChildren: () => import('./barril/barril.module').then(m => m.CraftBeerStoreBarrilModule)
      },
      {
        path: 'lote',
        loadChildren: () => import('./lote/lote.module').then(m => m.CraftBeerStoreLoteModule)
      },
      {
        path: 'tanque',
        loadChildren: () => import('./tanque/tanque.module').then(m => m.CraftBeerStoreTanqueModule)
      },
      {
        path: 'movimiento-barril',
        loadChildren: () => import('./movimiento-barril/movimiento-barril.module').then(m => m.CraftBeerStoreMovimientoBarrilModule)
      },
      {
        path: 'etapa-lote',
        loadChildren: () => import('./etapa-lote/etapa-lote.module').then(m => m.CraftBeerStoreEtapaLoteModule)
      },
      {
        path: 'movimiento-tanque',
        loadChildren: () => import('./movimiento-tanque/movimiento-tanque.module').then(m => m.CraftBeerStoreMovimientoTanqueModule)
      },
      {
        path: 'medicion-lote',
        loadChildren: () => import('./medicion-lote/medicion-lote.module').then(m => m.CraftBeerStoreMedicionLoteModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class CraftBeerStoreEntityModule {}
