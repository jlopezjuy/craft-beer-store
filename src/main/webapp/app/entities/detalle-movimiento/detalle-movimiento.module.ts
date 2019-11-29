import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CraftBeerStoreSharedModule } from 'app/shared/shared.module';
import { DetalleMovimientoComponent } from './detalle-movimiento.component';
import { DetalleMovimientoDetailComponent } from './detalle-movimiento-detail.component';
import { DetalleMovimientoUpdateComponent } from './detalle-movimiento-update.component';
import { DetalleMovimientoDeleteDialogComponent } from './detalle-movimiento-delete-dialog.component';
import { detalleMovimientoRoute } from './detalle-movimiento.route';

@NgModule({
  imports: [CraftBeerStoreSharedModule, RouterModule.forChild(detalleMovimientoRoute)],
  declarations: [
    DetalleMovimientoComponent,
    DetalleMovimientoDetailComponent,
    DetalleMovimientoUpdateComponent,
    DetalleMovimientoDeleteDialogComponent
  ],
  entryComponents: [DetalleMovimientoDeleteDialogComponent]
})
export class CraftBeerStoreDetalleMovimientoModule {}
