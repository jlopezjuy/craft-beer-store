import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEstilos } from 'app/shared/model/estilos.model';
import { EstilosService } from './estilos.service';

@Component({
  templateUrl: './estilos-delete-dialog.component.html'
})
export class EstilosDeleteDialogComponent {
  estilos: IEstilos;

  constructor(protected estilosService: EstilosService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.estilosService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'estilosListModification',
        content: 'Deleted an estilos'
      });
      this.activeModal.dismiss(true);
    });
  }
}
