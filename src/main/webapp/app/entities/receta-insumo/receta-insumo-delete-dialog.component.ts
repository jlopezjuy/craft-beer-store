import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRecetaInsumo } from 'app/shared/model/receta-insumo.model';
import { RecetaInsumoService } from './receta-insumo.service';

@Component({
  templateUrl: './receta-insumo-delete-dialog.component.html'
})
export class RecetaInsumoDeleteDialogComponent {
  recetaInsumo: IRecetaInsumo;

  constructor(
    protected recetaInsumoService: RecetaInsumoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.recetaInsumoService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'recetaInsumoListModification',
        content: 'Deleted an recetaInsumo'
      });
      this.activeModal.dismiss(true);
    });
  }
}
