import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEstilos } from 'app/shared/model/estilos.model';
import { EstilosService } from './estilos.service';

@Component({
    selector: 'jhi-estilos-delete-dialog',
    templateUrl: './estilos-delete-dialog.component.html'
})
export class EstilosDeleteDialogComponent {
    estilos: IEstilos;

    constructor(protected estilosService: EstilosService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.estilosService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'estilosListModification',
                content: 'Deleted an estilos'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-estilos-delete-popup',
    template: ''
})
export class EstilosDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ estilos }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(EstilosDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.estilos = estilos;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/estilos', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/estilos', { outlets: { popup: null } }]);
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
