import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CraftBeerStoreSharedModule } from 'app/shared/shared.module';
import { PuntoDeVentaComponent } from './punto-de-venta.component';
import { PuntoDeVentaDetailComponent } from './punto-de-venta-detail.component';
import { PuntoDeVentaUpdateComponent } from './punto-de-venta-update.component';
import { PuntoDeVentaDeleteDialogComponent } from './punto-de-venta-delete-dialog.component';
import { puntoDeVentaRoute } from './punto-de-venta.route';

@NgModule({
  imports: [CraftBeerStoreSharedModule, RouterModule.forChild(puntoDeVentaRoute)],
  declarations: [PuntoDeVentaComponent, PuntoDeVentaDetailComponent, PuntoDeVentaUpdateComponent, PuntoDeVentaDeleteDialogComponent],
  entryComponents: [PuntoDeVentaDeleteDialogComponent]
})
export class CraftBeerStorePuntoDeVentaModule {}
