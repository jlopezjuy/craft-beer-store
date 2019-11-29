import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CraftBeerStoreSharedModule } from 'app/shared/shared.module';
import { EstilosComponent } from './estilos.component';
import { EstilosDetailComponent } from './estilos-detail.component';
import { EstilosUpdateComponent } from './estilos-update.component';
import { EstilosDeleteDialogComponent } from './estilos-delete-dialog.component';
import { estilosRoute } from './estilos.route';

@NgModule({
  imports: [CraftBeerStoreSharedModule, RouterModule.forChild(estilosRoute)],
  declarations: [EstilosComponent, EstilosDetailComponent, EstilosUpdateComponent, EstilosDeleteDialogComponent],
  entryComponents: [EstilosDeleteDialogComponent]
})
export class CraftBeerStoreEstilosModule {}
