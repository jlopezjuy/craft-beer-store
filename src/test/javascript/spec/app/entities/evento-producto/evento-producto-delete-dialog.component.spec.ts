import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { CraftBeerStoreTestModule } from '../../../test.module';
import { EventoProductoDeleteDialogComponent } from 'app/entities/evento-producto/evento-producto-delete-dialog.component';
import { EventoProductoService } from 'app/entities/evento-producto/evento-producto.service';

describe('Component Tests', () => {
  describe('EventoProducto Management Delete Component', () => {
    let comp: EventoProductoDeleteDialogComponent;
    let fixture: ComponentFixture<EventoProductoDeleteDialogComponent>;
    let service: EventoProductoService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CraftBeerStoreTestModule],
        declarations: [EventoProductoDeleteDialogComponent]
      })
        .overrideTemplate(EventoProductoDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EventoProductoDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EventoProductoService);
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
