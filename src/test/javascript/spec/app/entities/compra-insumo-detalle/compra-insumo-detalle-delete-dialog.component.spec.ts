/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { CraftBeerStoreTestModule } from '../../../test.module';
import { CompraInsumoDetalleDeleteDialogComponent } from 'app/entities/compra-insumo-detalle/compra-insumo-detalle-delete-dialog.component';
import { CompraInsumoDetalleService } from 'app/entities/compra-insumo-detalle/compra-insumo-detalle.service';

describe('Component Tests', () => {
  describe('CompraInsumoDetalle Management Delete Component', () => {
    let comp: CompraInsumoDetalleDeleteDialogComponent;
    let fixture: ComponentFixture<CompraInsumoDetalleDeleteDialogComponent>;
    let service: CompraInsumoDetalleService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CraftBeerStoreTestModule],
        declarations: [CompraInsumoDetalleDeleteDialogComponent]
      })
        .overrideTemplate(CompraInsumoDetalleDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CompraInsumoDetalleDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CompraInsumoDetalleService);
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
