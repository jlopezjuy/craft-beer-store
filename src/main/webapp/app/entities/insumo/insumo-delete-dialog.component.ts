import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IInsumo } from 'app/shared/model/insumo.model';
import { InsumoService } from './insumo.service';

@Component({
  templateUrl: './insumo-delete-dialog.component.html'
})
export class InsumoDeleteDialogComponent {
  insumo: IInsumo;

  constructor(protected insumoService: InsumoService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.insumoService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'insumoListModification',
        content: 'Deleted an insumo'
      });
      this.activeModal.dismiss(true);
    });
  }
}
