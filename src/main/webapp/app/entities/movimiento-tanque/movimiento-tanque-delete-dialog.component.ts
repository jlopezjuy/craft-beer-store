import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMovimientoTanque } from 'app/shared/model/movimiento-tanque.model';
import { MovimientoTanqueService } from './movimiento-tanque.service';

@Component({
  templateUrl: './movimiento-tanque-delete-dialog.component.html'
})
export class MovimientoTanqueDeleteDialogComponent {
  movimientoTanque: IMovimientoTanque;

  constructor(
    protected movimientoTanqueService: MovimientoTanqueService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.movimientoTanqueService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'movimientoTanqueListModification',
        content: 'Deleted an movimientoTanque'
      });
      this.activeModal.dismiss(true);
    });
  }
}
