import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { CraftBeerStoreTestModule } from '../../../test.module';
import { EquipamientoDeleteDialogComponent } from 'app/entities/equipamiento/equipamiento-delete-dialog.component';
import { EquipamientoService } from 'app/entities/equipamiento/equipamiento.service';

describe('Component Tests', () => {
  describe('Equipamiento Management Delete Component', () => {
    let comp: EquipamientoDeleteDialogComponent;
    let fixture: ComponentFixture<EquipamientoDeleteDialogComponent>;
    let service: EquipamientoService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CraftBeerStoreTestModule],
        declarations: [EquipamientoDeleteDialogComponent]
      })
        .overrideTemplate(EquipamientoDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EquipamientoDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EquipamientoService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
