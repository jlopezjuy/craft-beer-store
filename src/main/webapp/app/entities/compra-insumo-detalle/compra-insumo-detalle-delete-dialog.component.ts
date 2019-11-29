import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICompraInsumoDetalle } from 'app/shared/model/compra-insumo-detalle.model';
import { CompraInsumoDetalleService } from './compra-insumo-detalle.service';

@Component({
  templateUrl: './compra-insumo-detalle-delete-dialog.component.html'
})
export class CompraInsumoDetalleDeleteDialogComponent {
  compraInsumoDetalle: ICompraInsumoDetalle;

  constructor(
    protected compraInsumoDetalleService: CompraInsumoDetalleService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.compraInsumoDetalleService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'compraInsumoDetalleListModification',
        content: 'Deleted an compraInsumoDetalle'
      });
      this.activeModal.dismiss(true);
    });
  }
}
