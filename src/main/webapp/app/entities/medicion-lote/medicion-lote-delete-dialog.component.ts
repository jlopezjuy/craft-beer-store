import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMedicionLote } from 'app/shared/model/medicion-lote.model';
import { MedicionLoteService } from './medicion-lote.service';

@Component({
  templateUrl: './medicion-lote-delete-dialog.component.html'
})
export class MedicionLoteDeleteDialogComponent {
  medicionLote: IMedicionLote;

  constructor(
    protected medicionLoteService: MedicionLoteService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.medicionLoteService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'medicionLoteListModification',
        content: 'Deleted an medicionLote'
      });
      this.activeModal.dismiss(true);
    });
  }
}
