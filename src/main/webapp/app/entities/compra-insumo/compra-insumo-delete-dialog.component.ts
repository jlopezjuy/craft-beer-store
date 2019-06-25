import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICompraInsumo } from 'app/shared/model/compra-insumo.model';
import { CompraInsumoService } from './compra-insumo.service';

@Component({
  selector: 'jhi-compra-insumo-delete-dialog',
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
    this.compraInsumoService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'compraInsumoListModification',
        content: 'Deleted an compraInsumo'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-compra-insumo-delete-popup',
  template: ''
})
export class CompraInsumoDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ compraInsumo }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(CompraInsumoDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.compraInsumo = compraInsumo;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/compra-insumo', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/compra-insumo', { outlets: { popup: null } }]);
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
