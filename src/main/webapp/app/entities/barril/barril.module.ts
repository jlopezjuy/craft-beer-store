import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CraftBeerStoreSharedModule } from 'app/shared/shared.module';
import { BarrilComponent } from './barril.component';
import { BarrilDetailComponent } from './barril-detail.component';
import { BarrilUpdateComponent } from './barril-update.component';
import { BarrilDeleteDialogComponent } from './barril-delete-dialog.component';
import { barrilRoute } from './barril.route';

@NgModule({
  imports: [CraftBeerStoreSharedModule, RouterModule.forChild(barrilRoute)],
  declarations: [BarrilComponent, BarrilDetailComponent, BarrilUpdateComponent, BarrilDeleteDialogComponent],
  entryComponents: [BarrilDeleteDialogComponent]
})
export class CraftBeerStoreBarrilModule {}
