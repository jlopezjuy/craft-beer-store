import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMovimientoBarril } from 'app/shared/model/movimiento-barril.model';
import { MovimientoBarrilService } from './movimiento-barril.service';

@Component({
  templateUrl: './movimiento-barril-delete-dialog.component.html'
})
export class MovimientoBarrilDeleteDialogComponent {
  movimientoBarril: IMovimientoBarril;

  constructor(
    protected movimientoBarrilService: MovimientoBarrilService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.movimientoBarrilService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'movimientoBarrilListModification',
        content: 'Deleted an movimientoBarril'
      });
      this.activeModal.dismiss(true);
    });
  }
}
