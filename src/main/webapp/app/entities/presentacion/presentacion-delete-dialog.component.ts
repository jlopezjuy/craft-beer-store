import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPresentacion } from 'app/shared/model/presentacion.model';
import { PresentacionService } from './presentacion.service';

@Component({
  templateUrl: './presentacion-delete-dialog.component.html'
})
export class PresentacionDeleteDialogComponent {
  presentacion: IPresentacion;

  constructor(
    protected presentacionService: PresentacionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.presentacionService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'presentacionListModification',
        content: 'Deleted an presentacion'
      });
      this.activeModal.dismiss(true);
    });
  }
}
