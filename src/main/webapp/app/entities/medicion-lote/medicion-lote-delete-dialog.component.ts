import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMedicionLote } from 'app/shared/model/medicion-lote.model';
import { MedicionLoteService } from './medicion-lote.service';

@Component({
  selector: 'jhi-medicion-lote-delete-dialog',
  templateUrl: './medicion-lote-delete-dialog.component.html'
})
export class MedicionLoteDeleteDialogComponent {
  medicionLote: IMedicionLote;

  constructor(
    protected medicionLoteService: MedicionLoteService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.medicionLoteService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'medicionLoteListModification',
        content: 'Deleted an medicionLote'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-medicion-lote-delete-popup',
  template: ''
})
export class MedicionLoteDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ medicionLote }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(MedicionLoteDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.medicionLote = medicionLote;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/medicion-lote', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/medicion-lote', { outlets: { popup: null } }]);
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
