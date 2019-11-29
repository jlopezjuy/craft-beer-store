import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { CraftBeerStoreTestModule } from '../../../test.module';
import { RecetaInsumoDeleteDialogComponent } from 'app/entities/receta-insumo/receta-insumo-delete-dialog.component';
import { RecetaInsumoService } from 'app/entities/receta-insumo/receta-insumo.service';

describe('Component Tests', () => {
  describe('RecetaInsumo Management Delete Component', () => {
    let comp: RecetaInsumoDeleteDialogComponent;
    let fixture: ComponentFixture<RecetaInsumoDeleteDialogComponent>;
    let service: RecetaInsumoService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CraftBeerStoreTestModule],
        declarations: [RecetaInsumoDeleteDialogComponent]
      })
        .overrideTemplate(RecetaInsumoDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RecetaInsumoDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RecetaInsumoService);
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
