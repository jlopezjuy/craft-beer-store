import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEventoProducto } from 'app/shared/model/evento-producto.model';
import { EventoProductoService } from './evento-producto.service';

@Component({
  templateUrl: './evento-producto-delete-dialog.component.html'
})
export class EventoProductoDeleteDialogComponent {
  eventoProducto: IEventoProducto;

  constructor(
    protected eventoProductoService: EventoProductoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.eventoProductoService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'eventoProductoListModification',
        content: 'Deleted an eventoProducto'
      });
      this.activeModal.dismiss(true);
    });
  }
}
