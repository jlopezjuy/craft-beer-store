import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CraftBeerStoreSharedModule } from 'app/shared/shared.module';
import { ProveedorComponent } from './proveedor.component';
import { ProveedorDetailComponent } from './proveedor-detail.component';
import { ProveedorUpdateComponent } from './proveedor-update.component';
import { ProveedorDeleteDialogComponent } from './proveedor-delete-dialog.component';
import { proveedorRoute } from './proveedor.route';

@NgModule({
  imports: [CraftBeerStoreSharedModule, RouterModule.forChild(proveedorRoute)],
  declarations: [ProveedorComponent, ProveedorDetailComponent, ProveedorUpdateComponent, ProveedorDeleteDialogComponent],
  entryComponents: [ProveedorDeleteDialogComponent]
})
export class CraftBeerStoreProveedorModule {}
