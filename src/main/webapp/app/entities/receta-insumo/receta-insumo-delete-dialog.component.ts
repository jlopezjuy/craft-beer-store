import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRecetaInsumo } from 'app/shared/model/receta-insumo.model';
import { RecetaInsumoService } from './receta-insumo.service';

@Component({
    selector: 'jhi-receta-insumo-delete-dialog',
    templateUrl: './receta-insumo-delete-dialog.component.html'
})
export class RecetaInsumoDeleteDialogComponent {
    recetaInsumo: IRecetaInsumo;

    constructor(
        protected recetaInsumoService: RecetaInsumoService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.recetaInsumoService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'recetaInsumoListModification',
                content: 'Deleted an recetaInsumo'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-receta-insumo-delete-popup',
    template: ''
})
export class RecetaInsumoDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ recetaInsumo }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(RecetaInsumoDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.recetaInsumo = recetaInsumo;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/receta-insumo', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/receta-insumo', { outlets: { popup: null } }]);
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
