import { Component, OnInit, OnDestroy, Inject } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITanque } from 'app/shared/model/tanque.model';
import { TanqueService } from './tanque.service';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';

@Component({
  selector: 'jhi-tanque-delete-dialog',
  templateUrl: './tanque-delete-dialog.component.html'
})
export class TanqueDeleteDialogComponent {
  tanque: ITanque;

  constructor(
    protected tanqueService: TanqueService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager,
    public dialogRef: MatDialogRef<TanqueDeleteDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: ITanque
  ) {}

  clear() {
    this.dialogRef.close();
  }

  confirmDelete() {
    this.tanqueService.delete(this.data.id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'tanqueListModification',
        content: 'Deleted an tanque'
      });
      this.dialogRef.close();
    });
  }
}

@Component({
  selector: 'jhi-tanque-delete-popup',
  template: ''
})
export class TanqueDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ tanque }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(TanqueDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.tanque = tanque;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/tanque', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/tanque', { outlets: { popup: null } }]);
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
