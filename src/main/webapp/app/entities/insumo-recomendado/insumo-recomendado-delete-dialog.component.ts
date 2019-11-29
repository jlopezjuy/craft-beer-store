import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IInsumoRecomendado } from 'app/shared/model/insumo-recomendado.model';
import { InsumoRecomendadoService } from './insumo-recomendado.service';

@Component({
  templateUrl: './insumo-recomendado-delete-dialog.component.html'
})
export class InsumoRecomendadoDeleteDialogComponent {
  insumoRecomendado: IInsumoRecomendado;

  constructor(
    protected insumoRecomendadoService: InsumoRecomendadoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.insumoRecomendadoService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'insumoRecomendadoListModification',
        content: 'Deleted an insumoRecomendado'
      });
      this.activeModal.dismiss(true);
    });
  }
}
