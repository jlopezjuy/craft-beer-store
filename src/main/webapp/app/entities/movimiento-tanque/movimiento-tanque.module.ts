import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CraftBeerStoreSharedModule } from 'app/shared/shared.module';
import { MovimientoTanqueComponent } from './movimiento-tanque.component';
import { MovimientoTanqueDetailComponent } from './movimiento-tanque-detail.component';
import { MovimientoTanqueUpdateComponent } from './movimiento-tanque-update.component';
import { MovimientoTanqueDeleteDialogComponent } from './movimiento-tanque-delete-dialog.component';
import { movimientoTanqueRoute } from './movimiento-tanque.route';

@NgModule({
  imports: [CraftBeerStoreSharedModule, RouterModule.forChild(movimientoTanqueRoute)],
  declarations: [
    MovimientoTanqueComponent,
    MovimientoTanqueDetailComponent,
    MovimientoTanqueUpdateComponent,
    MovimientoTanqueDeleteDialogComponent
  ],
  entryComponents: [MovimientoTanqueDeleteDialogComponent]
})
export class CraftBeerStoreMovimientoTanqueModule {}
