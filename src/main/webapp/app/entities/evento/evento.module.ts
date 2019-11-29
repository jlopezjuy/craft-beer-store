import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CraftBeerStoreSharedModule } from 'app/shared/shared.module';
import { EventoComponent } from './evento.component';
import { EventoDetailComponent } from './evento-detail.component';
import { EventoUpdateComponent } from './evento-update.component';
import { EventoDeleteDialogComponent } from './evento-delete-dialog.component';
import { eventoRoute } from './evento.route';

@NgModule({
  imports: [CraftBeerStoreSharedModule, RouterModule.forChild(eventoRoute)],
  declarations: [EventoComponent, EventoDetailComponent, EventoUpdateComponent, EventoDeleteDialogComponent],
  entryComponents: [EventoDeleteDialogComponent]
})
export class CraftBeerStoreEventoModule {}
