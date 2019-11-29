import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { CraftBeerStoreTestModule } from '../../../test.module';
import { CompraInsumoDeleteDialogComponent } from 'app/entities/compra-insumo/compra-insumo-delete-dialog.component';
import { CompraInsumoService } from 'app/entities/compra-insumo/compra-insumo.service';

describe('Component Tests', () => {
  describe('CompraInsumo Management Delete Component', () => {
    let comp: CompraInsumoDeleteDialogComponent;
    let fixture: ComponentFixture<CompraInsumoDeleteDialogComponent>;
    let service: CompraInsumoService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CraftBeerStoreTestModule],
        declarations: [CompraInsumoDeleteDialogComponent]
      })
        .overrideTemplate(CompraInsumoDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CompraInsumoDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CompraInsumoService);
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
