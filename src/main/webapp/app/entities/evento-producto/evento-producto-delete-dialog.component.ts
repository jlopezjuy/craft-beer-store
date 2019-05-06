import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEventoProducto } from 'app/shared/model/evento-producto.model';
import { EventoProductoService } from './evento-producto.service';

@Component({
    selector: 'jhi-evento-producto-delete-dialog',
    templateUrl: './evento-producto-delete-dialog.component.html'
})
export class EventoProductoDeleteDialogComponent {
    eventoProducto: IEventoProducto;

    constructor(
        protected eventoProductoService: EventoProductoService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.eventoProductoService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'eventoProductoListModification',
                content: 'Deleted an eventoProducto'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-evento-producto-delete-popup',
    template: ''
})
export class EventoProductoDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ eventoProducto }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(EventoProductoDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.eventoProducto = eventoProducto;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/evento-producto', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/evento-producto', { outlets: { popup: null } }]);
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
