import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CraftBeerStoreSharedModule } from 'app/shared/shared.module';
import { MovimientoBarrilComponent } from './movimiento-barril.component';
import { MovimientoBarrilDetailComponent } from './movimiento-barril-detail.component';
import { MovimientoBarrilUpdateComponent } from './movimiento-barril-update.component';
import { MovimientoBarrilDeleteDialogComponent } from './movimiento-barril-delete-dialog.component';
import { movimientoBarrilRoute } from './movimiento-barril.route';

@NgModule({
  imports: [CraftBeerStoreSharedModule, RouterModule.forChild(movimientoBarrilRoute)],
  declarations: [
    MovimientoBarrilComponent,
    MovimientoBarrilDetailComponent,
    MovimientoBarrilUpdateComponent,
    MovimientoBarrilDeleteDialogComponent
  ],
  entryComponents: [MovimientoBarrilDeleteDialogComponent]
})
export class CraftBeerStoreMovimientoBarrilModule {}
