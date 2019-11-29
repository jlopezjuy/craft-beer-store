import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CraftBeerStoreSharedModule } from 'app/shared/shared.module';
import { MedicionLoteComponent } from './medicion-lote.component';
import { MedicionLoteDetailComponent } from './medicion-lote-detail.component';
import { MedicionLoteUpdateComponent } from './medicion-lote-update.component';
import { MedicionLoteDeleteDialogComponent } from './medicion-lote-delete-dialog.component';
import { medicionLoteRoute } from './medicion-lote.route';

@NgModule({
  imports: [CraftBeerStoreSharedModule, RouterModule.forChild(medicionLoteRoute)],
  declarations: [MedicionLoteComponent, MedicionLoteDetailComponent, MedicionLoteUpdateComponent, MedicionLoteDeleteDialogComponent],
  entryComponents: [MedicionLoteDeleteDialogComponent]
})
export class CraftBeerStoreMedicionLoteModule {}
