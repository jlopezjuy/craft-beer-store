import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CraftBeerStoreSharedModule } from 'app/shared/shared.module';
import { CompraInsumoComponent } from './compra-insumo.component';
import { CompraInsumoDetailComponent } from './compra-insumo-detail.component';
import { CompraInsumoUpdateComponent } from './compra-insumo-update.component';
import { CompraInsumoDeleteDialogComponent } from './compra-insumo-delete-dialog.component';
import { compraInsumoRoute } from './compra-insumo.route';

@NgModule({
  imports: [CraftBeerStoreSharedModule, RouterModule.forChild(compraInsumoRoute)],
  declarations: [CompraInsumoComponent, CompraInsumoDetailComponent, CompraInsumoUpdateComponent, CompraInsumoDeleteDialogComponent],
  entryComponents: [CompraInsumoDeleteDialogComponent]
})
export class CraftBeerStoreCompraInsumoModule {}
