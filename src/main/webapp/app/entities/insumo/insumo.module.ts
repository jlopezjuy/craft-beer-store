import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CraftBeerStoreSharedModule } from 'app/shared/shared.module';
import { InsumoComponent } from './insumo.component';
import { InsumoDetailComponent } from './insumo-detail.component';
import { InsumoUpdateComponent } from './insumo-update.component';
import { InsumoDeleteDialogComponent } from './insumo-delete-dialog.component';
import { insumoRoute } from './insumo.route';

@NgModule({
  imports: [CraftBeerStoreSharedModule, RouterModule.forChild(insumoRoute)],
  declarations: [InsumoComponent, InsumoDetailComponent, InsumoUpdateComponent, InsumoDeleteDialogComponent],
  entryComponents: [InsumoDeleteDialogComponent]
})
export class CraftBeerStoreInsumoModule {}
