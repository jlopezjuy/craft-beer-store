import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CraftBeerStoreSharedModule } from 'app/shared/shared.module';
import { EquipamientoComponent } from './equipamiento.component';
import { EquipamientoDetailComponent } from './equipamiento-detail.component';
import { EquipamientoUpdateComponent } from './equipamiento-update.component';
import { EquipamientoDeleteDialogComponent } from './equipamiento-delete-dialog.component';
import { equipamientoRoute } from './equipamiento.route';

@NgModule({
  imports: [CraftBeerStoreSharedModule, RouterModule.forChild(equipamientoRoute)],
  declarations: [EquipamientoComponent, EquipamientoDetailComponent, EquipamientoUpdateComponent, EquipamientoDeleteDialogComponent],
  entryComponents: [EquipamientoDeleteDialogComponent]
})
export class CraftBeerStoreEquipamientoModule {}
