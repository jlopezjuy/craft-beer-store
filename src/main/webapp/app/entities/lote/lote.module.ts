import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CraftBeerStoreSharedModule } from 'app/shared/shared.module';
import { LoteComponent } from './lote.component';
import { LoteDetailComponent } from './lote-detail.component';
import { LoteUpdateComponent } from './lote-update.component';
import { LoteDeleteDialogComponent } from './lote-delete-dialog.component';
import { loteRoute } from './lote.route';

@NgModule({
  imports: [CraftBeerStoreSharedModule, RouterModule.forChild(loteRoute)],
  declarations: [LoteComponent, LoteDetailComponent, LoteUpdateComponent, LoteDeleteDialogComponent],
  entryComponents: [LoteDeleteDialogComponent]
})
export class CraftBeerStoreLoteModule {}
