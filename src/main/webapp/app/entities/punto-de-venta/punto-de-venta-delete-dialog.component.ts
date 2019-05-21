import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPuntoDeVenta } from 'app/shared/model/punto-de-venta.model';
import { PuntoDeVentaService } from './punto-de-venta.service';

@Component({
    selector: 'jhi-punto-de-venta-delete-dialog',
    templateUrl: './punto-de-venta-delete-dialog.component.html'
})
export class PuntoDeVentaDeleteDialogComponent {
    puntoDeVenta: IPuntoDeVenta;

    constructor(
        protected puntoDeVentaService: PuntoDeVentaService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.puntoDeVentaService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'puntoDeVentaListModification',
                content: 'Deleted an puntoDeVenta'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-punto-de-venta-delete-popup',
    template: ''
})
export class PuntoDeVentaDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ puntoDeVenta }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(PuntoDeVentaDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.puntoDeVenta = puntoDeVenta;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/punto-de-venta', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/punto-de-venta', { outlets: { popup: null } }]);
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
