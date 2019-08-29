import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICompraInsumoDetalle } from 'app/shared/model/compra-insumo-detalle.model';
import { CompraInsumoDetalleService } from './compra-insumo-detalle.service';

@Component({
  selector: 'jhi-compra-insumo-detalle-delete-dialog',
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
    this.compraInsumoDetalleService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'compraInsumoDetalleListModification',
        content: 'Deleted an compraInsumoDetalle'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-compra-insumo-detalle-delete-popup',
  template: ''
})
export class CompraInsumoDetalleDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ compraInsumoDetalle }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(CompraInsumoDetalleDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.compraInsumoDetalle = compraInsumoDetalle;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/compra-insumo-detalle', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/compra-insumo-detalle', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
