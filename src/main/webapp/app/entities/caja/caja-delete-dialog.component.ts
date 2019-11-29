import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICaja } from 'app/shared/model/caja.model';
import { CajaService } from './caja.service';

@Component({
  templateUrl: './caja-delete-dialog.component.html'
})
export class CajaDeleteDialogComponent {
  caja: ICaja;

  constructor(protected cajaService: CajaService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.cajaService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'cajaListModification',
        content: 'Deleted an caja'
      });
      this.activeModal.dismiss(true);
    });
  }
}
