/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { CraftBeerStoreTestModule } from '../../../test.module';
import { MovimientoBarrilDeleteDialogComponent } from 'app/entities/movimiento-barril/movimiento-barril-delete-dialog.component';
import { MovimientoBarrilService } from 'app/entities/movimiento-barril/movimiento-barril.service';

describe('Component Tests', () => {
  describe('MovimientoBarril Management Delete Component', () => {
    let comp: MovimientoBarrilDeleteDialogComponent;
    let fixture: ComponentFixture<MovimientoBarrilDeleteDialogComponent>;
    let service: MovimientoBarrilService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CraftBeerStoreTestModule],
        declarations: [MovimientoBarrilDeleteDialogComponent]
      })
        .overrideTemplate(MovimientoBarrilDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MovimientoBarrilDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MovimientoBarrilService);
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
