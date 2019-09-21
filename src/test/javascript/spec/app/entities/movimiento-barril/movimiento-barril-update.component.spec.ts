/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { CraftBeerStoreTestModule } from '../../../test.module';
import { MovimientoBarrilUpdateComponent } from 'app/entities/movimiento-barril/movimiento-barril-update.component';
import { MovimientoBarrilService } from 'app/entities/movimiento-barril/movimiento-barril.service';
import { MovimientoBarril } from 'app/shared/model/movimiento-barril.model';

describe('Component Tests', () => {
  describe('MovimientoBarril Management Update Component', () => {
    let comp: MovimientoBarrilUpdateComponent;
    let fixture: ComponentFixture<MovimientoBarrilUpdateComponent>;
    let service: MovimientoBarrilService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CraftBeerStoreTestModule],
        declarations: [MovimientoBarrilUpdateComponent]
      })
        .overrideTemplate(MovimientoBarrilUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MovimientoBarrilUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MovimientoBarrilService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new MovimientoBarril(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.movimientoBarril = entity;
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new MovimientoBarril();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.movimientoBarril = entity;
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
