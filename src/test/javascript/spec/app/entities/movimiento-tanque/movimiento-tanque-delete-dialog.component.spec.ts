/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { CraftBeerStoreTestModule } from '../../../test.module';
import { MovimientoTanqueDeleteDialogComponent } from 'app/entities/movimiento-tanque/movimiento-tanque-delete-dialog.component';
import { MovimientoTanqueService } from 'app/entities/movimiento-tanque/movimiento-tanque.service';

describe('Component Tests', () => {
  describe('MovimientoTanque Management Delete Component', () => {
    let comp: MovimientoTanqueDeleteDialogComponent;
    let fixture: ComponentFixture<MovimientoTanqueDeleteDialogComponent>;
    let service: MovimientoTanqueService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CraftBeerStoreTestModule],
        declarations: [MovimientoTanqueDeleteDialogComponent]
      })
        .overrideTemplate(MovimientoTanqueDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MovimientoTanqueDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MovimientoTanqueService);
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
