import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CraftBeerStoreSharedModule } from 'app/shared/shared.module';
import { RecetaComponent } from './receta.component';
import { RecetaDetailComponent } from './receta-detail.component';
import { RecetaUpdateComponent } from './receta-update.component';
import { RecetaDeleteDialogComponent } from './receta-delete-dialog.component';
import { recetaRoute } from './receta.route';

@NgModule({
  imports: [CraftBeerStoreSharedModule, RouterModule.forChild(recetaRoute)],
  declarations: [RecetaComponent, RecetaDetailComponent, RecetaUpdateComponent, RecetaDeleteDialogComponent],
  entryComponents: [RecetaDeleteDialogComponent]
})
export class CraftBeerStoreRecetaModule {}
