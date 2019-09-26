import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEtapaLote } from 'app/shared/model/etapa-lote.model';
import { EtapaLoteService } from './etapa-lote.service';

@Component({
  selector: 'jhi-etapa-lote-delete-dialog',
  templateUrl: './etapa-lote-delete-dialog.component.html'
})
export class EtapaLoteDeleteDialogComponent {
  etapaLote: IEtapaLote;

  constructor(protected etapaLoteService: EtapaLoteService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.etapaLoteService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'etapaLoteListModification',
        content: 'Deleted an etapaLote'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-etapa-lote-delete-popup',
  template: ''
})
export class EtapaLoteDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ etapaLote }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(EtapaLoteDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.etapaLote = etapaLote;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/etapa-lote', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/etapa-lote', { outlets: { popup: null } }]);
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
