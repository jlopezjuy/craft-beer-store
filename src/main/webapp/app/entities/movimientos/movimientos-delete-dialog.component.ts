import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMovimientos } from 'app/shared/model/movimientos.model';
import { MovimientosService } from './movimientos.service';

@Component({
  templateUrl: './movimientos-delete-dialog.component.html'
})
export class MovimientosDeleteDialogComponent {
  movimientos: IMovimientos;

  constructor(
    protected movimientosService: MovimientosService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.movimientosService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'movimientosListModification',
        content: 'Deleted an movimientos'
      });
      this.activeModal.dismiss(true);
    });
  }
}
