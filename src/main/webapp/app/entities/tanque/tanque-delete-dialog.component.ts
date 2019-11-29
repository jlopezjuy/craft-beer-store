import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITanque } from 'app/shared/model/tanque.model';
import { TanqueService } from './tanque.service';

@Component({
  templateUrl: './tanque-delete-dialog.component.html'
})
export class TanqueDeleteDialogComponent {
  tanque: ITanque;

  constructor(protected tanqueService: TanqueService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.tanqueService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'tanqueListModification',
        content: 'Deleted an tanque'
      });
      this.activeModal.dismiss(true);
    });
  }
}
