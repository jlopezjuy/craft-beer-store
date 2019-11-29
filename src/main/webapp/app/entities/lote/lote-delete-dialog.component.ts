import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILote } from 'app/shared/model/lote.model';
import { LoteService } from './lote.service';

@Component({
  templateUrl: './lote-delete-dialog.component.html'
})
export class LoteDeleteDialogComponent {
  lote: ILote;

  constructor(protected loteService: LoteService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.loteService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'loteListModification',
        content: 'Deleted an lote'
      });
      this.activeModal.dismiss(true);
    });
  }
}
