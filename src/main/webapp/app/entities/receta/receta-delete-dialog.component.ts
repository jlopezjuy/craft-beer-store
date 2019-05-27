import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IReceta } from 'app/shared/model/receta.model';
import { RecetaService } from './receta.service';

@Component({
    selector: 'jhi-receta-delete-dialog',
    templateUrl: './receta-delete-dialog.component.html'
})
export class RecetaDeleteDialogComponent {
    receta: IReceta;

    constructor(protected recetaService: RecetaService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.recetaService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'recetaListModification',
                content: 'Deleted an receta'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-receta-delete-popup',
    template: ''
})
export class RecetaDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ receta }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(RecetaDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.receta = receta;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/receta', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/receta', { outlets: { popup: null } }]);
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
