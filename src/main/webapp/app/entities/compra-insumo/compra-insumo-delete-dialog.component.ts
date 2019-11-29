import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICompraInsumo } from 'app/shared/model/compra-insumo.model';
import { CompraInsumoService } from './compra-insumo.service';

@Component({
  templateUrl: './compra-insumo-delete-dialog.component.html'
})
export class CompraInsumoDeleteDialogComponent {
  compraInsumo: ICompraInsumo;

  constructor(
    protected compraInsumoService: CompraInsumoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.compraInsumoService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'compraInsumoListModification',
        content: 'Deleted an compraInsumo'
      });
      this.activeModal.dismiss(true);
    });
  }
}
