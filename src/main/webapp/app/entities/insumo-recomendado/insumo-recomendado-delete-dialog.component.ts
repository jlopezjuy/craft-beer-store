import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IInsumoRecomendado } from 'app/shared/model/insumo-recomendado.model';
import { InsumoRecomendadoService } from './insumo-recomendado.service';

@Component({
    selector: 'jhi-insumo-recomendado-delete-dialog',
    templateUrl: './insumo-recomendado-delete-dialog.component.html'
})
export class InsumoRecomendadoDeleteDialogComponent {
    insumoRecomendado: IInsumoRecomendado;

    constructor(
        protected insumoRecomendadoService: InsumoRecomendadoService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.insumoRecomendadoService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'insumoRecomendadoListModification',
                content: 'Deleted an insumoRecomendado'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-insumo-recomendado-delete-popup',
    template: ''
})
export class InsumoRecomendadoDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ insumoRecomendado }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(InsumoRecomendadoDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.insumoRecomendado = insumoRecomendado;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/insumo-recomendado', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/insumo-recomendado', { outlets: { popup: null } }]);
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
