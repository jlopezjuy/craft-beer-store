import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CraftBeerStoreSharedModule } from 'app/shared/shared.module';
import { CajaComponent } from './caja.component';
import { CajaDetailComponent } from './caja-detail.component';
import { CajaUpdateComponent } from './caja-update.component';
import { CajaDeleteDialogComponent } from './caja-delete-dialog.component';
import { cajaRoute } from './caja.route';

@NgModule({
  imports: [CraftBeerStoreSharedModule, RouterModule.forChild(cajaRoute)],
  declarations: [CajaComponent, CajaDetailComponent, CajaUpdateComponent, CajaDeleteDialogComponent],
  entryComponents: [CajaDeleteDialogComponent]
})
export class CraftBeerStoreCajaModule {}
