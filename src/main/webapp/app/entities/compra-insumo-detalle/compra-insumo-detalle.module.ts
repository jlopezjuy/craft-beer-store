import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CraftBeerStoreSharedModule } from 'app/shared/shared.module';
import { CompraInsumoDetalleComponent } from './compra-insumo-detalle.component';
import { CompraInsumoDetalleDetailComponent } from './compra-insumo-detalle-detail.component';
import { CompraInsumoDetalleUpdateComponent } from './compra-insumo-detalle-update.component';
import { CompraInsumoDetalleDeleteDialogComponent } from './compra-insumo-detalle-delete-dialog.component';
import { compraInsumoDetalleRoute } from './compra-insumo-detalle.route';

@NgModule({
  imports: [CraftBeerStoreSharedModule, RouterModule.forChild(compraInsumoDetalleRoute)],
  declarations: [
    CompraInsumoDetalleComponent,
    CompraInsumoDetalleDetailComponent,
    CompraInsumoDetalleUpdateComponent,
    CompraInsumoDetalleDeleteDialogComponent
  ],
  entryComponents: [CompraInsumoDetalleDeleteDialogComponent]
})
export class CraftBeerStoreCompraInsumoDetalleModule {}
