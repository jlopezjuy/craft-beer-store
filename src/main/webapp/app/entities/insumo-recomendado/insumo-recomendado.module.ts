import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CraftBeerStoreSharedModule } from 'app/shared/shared.module';
import { InsumoRecomendadoComponent } from './insumo-recomendado.component';
import { InsumoRecomendadoDetailComponent } from './insumo-recomendado-detail.component';
import { InsumoRecomendadoUpdateComponent } from './insumo-recomendado-update.component';
import { InsumoRecomendadoDeleteDialogComponent } from './insumo-recomendado-delete-dialog.component';
import { insumoRecomendadoRoute } from './insumo-recomendado.route';

@NgModule({
  imports: [CraftBeerStoreSharedModule, RouterModule.forChild(insumoRecomendadoRoute)],
  declarations: [
    InsumoRecomendadoComponent,
    InsumoRecomendadoDetailComponent,
    InsumoRecomendadoUpdateComponent,
    InsumoRecomendadoDeleteDialogComponent
  ],
  entryComponents: [InsumoRecomendadoDeleteDialogComponent]
})
export class CraftBeerStoreInsumoRecomendadoModule {}
