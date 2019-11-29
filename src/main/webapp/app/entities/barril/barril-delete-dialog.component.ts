import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBarril } from 'app/shared/model/barril.model';
import { BarrilService } from './barril.service';

@Component({
  templateUrl: './barril-delete-dialog.component.html'
})
export class BarrilDeleteDialogComponent {
  barril: IBarril;

  constructor(protected barrilService: BarrilService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.barrilService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'barrilListModification',
        content: 'Deleted an barril'
      });
      this.activeModal.dismiss(true);
    });
  }
}
