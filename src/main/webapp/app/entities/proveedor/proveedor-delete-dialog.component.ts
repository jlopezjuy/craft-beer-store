import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProveedor } from 'app/shared/model/proveedor.model';
import { ProveedorService } from './proveedor.service';

@Component({
  templateUrl: './proveedor-delete-dialog.component.html'
})
export class ProveedorDeleteDialogComponent {
  proveedor: IProveedor;

  constructor(protected proveedorService: ProveedorService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.proveedorService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'proveedorListModification',
        content: 'Deleted an proveedor'
      });
      this.activeModal.dismiss(true);
    });
  }
}
