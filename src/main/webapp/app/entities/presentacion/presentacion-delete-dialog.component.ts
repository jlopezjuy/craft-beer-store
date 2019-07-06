import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPresentacion } from 'app/shared/model/presentacion.model';
import { PresentacionService } from './presentacion.service';

@Component({
  selector: 'jhi-presentacion-delete-dialog',
  templateUrl: './presentacion-delete-dialog.component.html'
})
export class PresentacionDeleteDialogComponent {
  presentacion: IPresentacion;

  constructor(
    protected presentacionService: PresentacionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.presentacionService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'presentacionListModification',
        content: 'Deleted an presentacion'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-presentacion-delete-popup',
  template: ''
})
export class PresentacionDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ presentacion }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(PresentacionDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.presentacion = presentacion;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/presentacion', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/presentacion', { outlets: { popup: null } }]);
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
