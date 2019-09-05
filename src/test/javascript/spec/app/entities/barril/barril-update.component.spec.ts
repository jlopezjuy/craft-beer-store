/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { CraftBeerStoreTestModule } from '../../../test.module';
import { BarrilUpdateComponent } from 'app/entities/barril/barril-update.component';
import { BarrilService } from 'app/entities/barril/barril.service';
import { Barril } from 'app/shared/model/barril.model';

describe('Component Tests', () => {
  describe('Barril Management Update Component', () => {
    let comp: BarrilUpdateComponent;
    let fixture: ComponentFixture<BarrilUpdateComponent>;
    let service: BarrilService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CraftBeerStoreTestModule],
        declarations: [BarrilUpdateComponent]
      })
        .overrideTemplate(BarrilUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BarrilUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BarrilService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Barril(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.barril = entity;
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Barril();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.barril = entity;
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
