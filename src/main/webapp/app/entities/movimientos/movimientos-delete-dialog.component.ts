import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMovimientos } from 'app/shared/model/movimientos.model';
import { MovimientosService } from './movimientos.service';

@Component({
    selector: 'jhi-movimientos-delete-dialog',
    templateUrl: './movimientos-delete-dialog.component.html'
})
export class MovimientosDeleteDialogComponent {
    movimientos: IMovimientos;

    constructor(
        protected movimientosService: MovimientosService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.movimientosService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'movimientosListModification',
                content: 'Deleted an movimientos'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-movimientos-delete-popup',
    template: ''
})
export class MovimientosDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ movimientos }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(MovimientosDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.movimientos = movimientos;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/movimientos', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/movimientos', { outlets: { popup: null } }]);
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
