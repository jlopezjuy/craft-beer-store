import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPuntoDeVenta } from 'app/shared/model/punto-de-venta.model';
import { PuntoDeVentaService } from './punto-de-venta.service';

@Component({
  templateUrl: './punto-de-venta-delete-dialog.component.html'
})
export class PuntoDeVentaDeleteDialogComponent {
  puntoDeVenta: IPuntoDeVenta;

  constructor(
    protected puntoDeVentaService: PuntoDeVentaService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.puntoDeVentaService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'puntoDeVentaListModification',
        content: 'Deleted an puntoDeVenta'
      });
      this.activeModal.dismiss(true);
    });
  }
}
