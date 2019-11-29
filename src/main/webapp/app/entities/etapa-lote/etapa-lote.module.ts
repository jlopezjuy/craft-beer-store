import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CraftBeerStoreSharedModule } from 'app/shared/shared.module';
import { EtapaLoteComponent } from './etapa-lote.component';
import { EtapaLoteDetailComponent } from './etapa-lote-detail.component';
import { EtapaLoteUpdateComponent } from './etapa-lote-update.component';
import { EtapaLoteDeleteDialogComponent } from './etapa-lote-delete-dialog.component';
import { etapaLoteRoute } from './etapa-lote.route';

@NgModule({
  imports: [CraftBeerStoreSharedModule, RouterModule.forChild(etapaLoteRoute)],
  declarations: [EtapaLoteComponent, EtapaLoteDetailComponent, EtapaLoteUpdateComponent, EtapaLoteDeleteDialogComponent],
  entryComponents: [EtapaLoteDeleteDialogComponent]
})
export class CraftBeerStoreEtapaLoteModule {}
