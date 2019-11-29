import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CraftBeerStoreSharedModule } from 'app/shared/shared.module';
import { MovimientosComponent } from './movimientos.component';
import { MovimientosDetailComponent } from './movimientos-detail.component';
import { MovimientosUpdateComponent } from './movimientos-update.component';
import { MovimientosDeleteDialogComponent } from './movimientos-delete-dialog.component';
import { movimientosRoute } from './movimientos.route';

@NgModule({
  imports: [CraftBeerStoreSharedModule, RouterModule.forChild(movimientosRoute)],
  declarations: [MovimientosComponent, MovimientosDetailComponent, MovimientosUpdateComponent, MovimientosDeleteDialogComponent],
  entryComponents: [MovimientosDeleteDialogComponent]
})
export class CraftBeerStoreMovimientosModule {}
