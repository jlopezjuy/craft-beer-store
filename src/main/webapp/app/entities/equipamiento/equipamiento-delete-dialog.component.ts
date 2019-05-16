import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEquipamiento } from 'app/shared/model/equipamiento.model';
import { EquipamientoService } from './equipamiento.service';

@Component({
    selector: 'jhi-equipamiento-delete-dialog',
    templateUrl: './equipamiento-delete-dialog.component.html'
})
export class EquipamientoDeleteDialogComponent {
    equipamiento: IEquipamiento;

    constructor(
        protected equipamientoService: EquipamientoService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.equipamientoService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'equipamientoListModification',
                content: 'Deleted an equipamiento'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-equipamiento-delete-popup',
    template: ''
})
export class EquipamientoDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ equipamiento }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(EquipamientoDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.equipamiento = equipamiento;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/equipamiento', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/equipamiento', { outlets: { popup: null } }]);
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
