import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEvento } from 'app/shared/model/evento.model';
import { EventoService } from './evento.service';

@Component({
  templateUrl: './evento-delete-dialog.component.html'
})
export class EventoDeleteDialogComponent {
  evento: IEvento;

  constructor(protected eventoService: EventoService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.eventoService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'eventoListModification',
        content: 'Deleted an evento'
      });
      this.activeModal.dismiss(true);
    });
  }
}
