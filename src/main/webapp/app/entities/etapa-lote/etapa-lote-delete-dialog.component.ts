import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEtapaLote } from 'app/shared/model/etapa-lote.model';
import { EtapaLoteService } from './etapa-lote.service';

@Component({
  templateUrl: './etapa-lote-delete-dialog.component.html'
})
export class EtapaLoteDeleteDialogComponent {
  etapaLote: IEtapaLote;

  constructor(protected etapaLoteService: EtapaLoteService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.etapaLoteService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'etapaLoteListModification',
        content: 'Deleted an etapaLote'
      });
      this.activeModal.dismiss(true);
    });
  }
}
