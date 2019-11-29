import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CraftBeerStoreSharedModule } from 'app/shared/shared.module';
import { TanqueComponent } from './tanque.component';
import { TanqueDetailComponent } from './tanque-detail.component';
import { TanqueUpdateComponent } from './tanque-update.component';
import { TanqueDeleteDialogComponent } from './tanque-delete-dialog.component';
import { tanqueRoute } from './tanque.route';

@NgModule({
  imports: [CraftBeerStoreSharedModule, RouterModule.forChild(tanqueRoute)],
  declarations: [TanqueComponent, TanqueDetailComponent, TanqueUpdateComponent, TanqueDeleteDialogComponent],
  entryComponents: [TanqueDeleteDialogComponent]
})
export class CraftBeerStoreTanqueModule {}
