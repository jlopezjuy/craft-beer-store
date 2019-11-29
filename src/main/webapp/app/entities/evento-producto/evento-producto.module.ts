import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CraftBeerStoreSharedModule } from 'app/shared/shared.module';
import { EventoProductoComponent } from './evento-producto.component';
import { EventoProductoDetailComponent } from './evento-producto-detail.component';
import { EventoProductoUpdateComponent } from './evento-producto-update.component';
import { EventoProductoDeleteDialogComponent } from './evento-producto-delete-dialog.component';
import { eventoProductoRoute } from './evento-producto.route';

@NgModule({
  imports: [CraftBeerStoreSharedModule, RouterModule.forChild(eventoProductoRoute)],
  declarations: [
    EventoProductoComponent,
    EventoProductoDetailComponent,
    EventoProductoUpdateComponent,
    EventoProductoDeleteDialogComponent
  ],
  entryComponents: [EventoProductoDeleteDialogComponent]
})
export class CraftBeerStoreEventoProductoModule {}
