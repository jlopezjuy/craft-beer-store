/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { CraftBeerStoreTestModule } from '../../../test.module';
import { MedicionLoteDeleteDialogComponent } from 'app/entities/medicion-lote/medicion-lote-delete-dialog.component';
import { MedicionLoteService } from 'app/entities/medicion-lote/medicion-lote.service';

describe('Component Tests', () => {
  describe('MedicionLote Management Delete Component', () => {
    let comp: MedicionLoteDeleteDialogComponent;
    let fixture: ComponentFixture<MedicionLoteDeleteDialogComponent>;
    let service: MedicionLoteService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CraftBeerStoreTestModule],
        declarations: [MedicionLoteDeleteDialogComponent]
      })
        .overrideTemplate(MedicionLoteDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MedicionLoteDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MedicionLoteService);
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
