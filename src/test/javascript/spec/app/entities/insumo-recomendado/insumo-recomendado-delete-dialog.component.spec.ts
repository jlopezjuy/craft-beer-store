import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { CraftBeerStoreTestModule } from '../../../test.module';
import { InsumoRecomendadoDeleteDialogComponent } from 'app/entities/insumo-recomendado/insumo-recomendado-delete-dialog.component';
import { InsumoRecomendadoService } from 'app/entities/insumo-recomendado/insumo-recomendado.service';

describe('Component Tests', () => {
  describe('InsumoRecomendado Management Delete Component', () => {
    let comp: InsumoRecomendadoDeleteDialogComponent;
    let fixture: ComponentFixture<InsumoRecomendadoDeleteDialogComponent>;
    let service: InsumoRecomendadoService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CraftBeerStoreTestModule],
        declarations: [InsumoRecomendadoDeleteDialogComponent]
      })
        .overrideTemplate(InsumoRecomendadoDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(InsumoRecomendadoDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(InsumoRecomendadoService);
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
