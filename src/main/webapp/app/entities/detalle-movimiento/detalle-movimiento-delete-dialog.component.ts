import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDetalleMovimiento } from 'app/shared/model/detalle-movimiento.model';
import { DetalleMovimientoService } from './detalle-movimiento.service';

@Component({
  templateUrl: './detalle-movimiento-delete-dialog.component.html'
})
export class DetalleMovimientoDeleteDialogComponent {
  detalleMovimiento: IDetalleMovimiento;

  constructor(
    protected detalleMovimientoService: DetalleMovimientoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.detalleMovimientoService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'detalleMovimientoListModification',
        content: 'Deleted an detalleMovimiento'
      });
      this.activeModal.dismiss(true);
    });
  }
}
