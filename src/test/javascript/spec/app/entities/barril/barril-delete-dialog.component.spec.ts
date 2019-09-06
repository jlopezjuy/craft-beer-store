/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { CraftBeerStoreTestModule } from '../../../test.module';
import { BarrilDeleteDialogComponent } from 'app/entities/barril/barril-delete-dialog.component';
import { BarrilService } from 'app/entities/barril/barril.service';

describe('Component Tests', () => {
  describe('Barril Management Delete Component', () => {
    let comp: BarrilDeleteDialogComponent;
    let fixture: ComponentFixture<BarrilDeleteDialogComponent>;
    let service: BarrilService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CraftBeerStoreTestModule],
        declarations: [BarrilDeleteDialogComponent]
      })
        .overrideTemplate(BarrilDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BarrilDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BarrilService);
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
          // comp.confirmDelete(123);
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
