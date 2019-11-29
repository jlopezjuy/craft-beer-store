import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IReceta } from 'app/shared/model/receta.model';
import { RecetaService } from './receta.service';

@Component({
  templateUrl: './receta-delete-dialog.component.html'
})
export class RecetaDeleteDialogComponent {
  receta: IReceta;

  constructor(protected recetaService: RecetaService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.recetaService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'recetaListModification',
        content: 'Deleted an receta'
      });
      this.activeModal.dismiss(true);
    });
  }
}
