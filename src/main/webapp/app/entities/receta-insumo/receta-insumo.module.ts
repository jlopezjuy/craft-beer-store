import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CraftBeerStoreSharedModule } from 'app/shared/shared.module';
import { RecetaInsumoComponent } from './receta-insumo.component';
import { RecetaInsumoDetailComponent } from './receta-insumo-detail.component';
import { RecetaInsumoUpdateComponent } from './receta-insumo-update.component';
import { RecetaInsumoDeleteDialogComponent } from './receta-insumo-delete-dialog.component';
import { recetaInsumoRoute } from './receta-insumo.route';

@NgModule({
  imports: [CraftBeerStoreSharedModule, RouterModule.forChild(recetaInsumoRoute)],
  declarations: [RecetaInsumoComponent, RecetaInsumoDetailComponent, RecetaInsumoUpdateComponent, RecetaInsumoDeleteDialogComponent],
  entryComponents: [RecetaInsumoDeleteDialogComponent]
})
export class CraftBeerStoreRecetaInsumoModule {}
