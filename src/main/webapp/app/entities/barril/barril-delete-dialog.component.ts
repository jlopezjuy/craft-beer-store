import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBarril } from 'app/shared/model/barril.model';
import { BarrilService } from './barril.service';

@Component({
  selector: 'jhi-barril-delete-dialog',
  templateUrl: './barril-delete-dialog.component.html'
})
export class BarrilDeleteDialogComponent {
  barril: IBarril;

  constructor(protected barrilService: BarrilService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.barrilService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'barrilListModification',
        content: 'Deleted an barril'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-barril-delete-popup',
  template: ''
})
export class BarrilDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ barril }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(BarrilDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.barril = barril;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/barril', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/barril', { outlets: { popup: null } }]);
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
