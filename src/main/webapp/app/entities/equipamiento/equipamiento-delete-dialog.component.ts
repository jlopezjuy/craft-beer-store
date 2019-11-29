import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEquipamiento } from 'app/shared/model/equipamiento.model';
import { EquipamientoService } from './equipamiento.service';

@Component({
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
    this.equipamientoService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'equipamientoListModification',
        content: 'Deleted an equipamiento'
      });
      this.activeModal.dismiss(true);
    });
  }
}
