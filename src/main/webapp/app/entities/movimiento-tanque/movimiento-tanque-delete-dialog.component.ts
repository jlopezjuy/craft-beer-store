import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMovimientoTanque } from 'app/shared/model/movimiento-tanque.model';
import { MovimientoTanqueService } from './movimiento-tanque.service';

@Component({
  selector: 'jhi-movimiento-tanque-delete-dialog',
  templateUrl: './movimiento-tanque-delete-dialog.component.html'
})
export class MovimientoTanqueDeleteDialogComponent {
  movimientoTanque: IMovimientoTanque;

  constructor(
    protected movimientoTanqueService: MovimientoTanqueService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.movimientoTanqueService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'movimientoTanqueListModification',
        content: 'Deleted an movimientoTanque'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-movimiento-tanque-delete-popup',
  template: ''
})
export class MovimientoTanqueDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ movimientoTanque }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(MovimientoTanqueDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.movimientoTanque = movimientoTanque;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/movimiento-tanque', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/movimiento-tanque', { outlets: { popup: null } }]);
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
