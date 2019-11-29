import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { CraftBeerStoreTestModule } from '../../../test.module';
import { MovimientoTanqueUpdateComponent } from 'app/entities/movimiento-tanque/movimiento-tanque-update.component';
import { MovimientoTanqueService } from 'app/entities/movimiento-tanque/movimiento-tanque.service';
import { MovimientoTanque } from 'app/shared/model/movimiento-tanque.model';

describe('Component Tests', () => {
  describe('MovimientoTanque Management Update Component', () => {
    let comp: MovimientoTanqueUpdateComponent;
    let fixture: ComponentFixture<MovimientoTanqueUpdateComponent>;
    let service: MovimientoTanqueService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CraftBeerStoreTestModule],
        declarations: [MovimientoTanqueUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(MovimientoTanqueUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MovimientoTanqueUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MovimientoTanqueService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new MovimientoTanque(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new MovimientoTanque();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
