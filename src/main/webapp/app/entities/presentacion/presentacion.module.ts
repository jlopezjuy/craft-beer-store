import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CraftBeerStoreSharedModule } from 'app/shared/shared.module';
import { PresentacionComponent } from './presentacion.component';
import { PresentacionDetailComponent } from './presentacion-detail.component';
import { PresentacionUpdateComponent } from './presentacion-update.component';
import { PresentacionDeleteDialogComponent } from './presentacion-delete-dialog.component';
import { presentacionRoute } from './presentacion.route';

@NgModule({
  imports: [CraftBeerStoreSharedModule, RouterModule.forChild(presentacionRoute)],
  declarations: [PresentacionComponent, PresentacionDetailComponent, PresentacionUpdateComponent, PresentacionDeleteDialogComponent],
  entryComponents: [PresentacionDeleteDialogComponent]
})
export class CraftBeerStorePresentacionModule {}
