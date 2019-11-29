import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { CraftBeerStoreTestModule } from '../../../test.module';
import { TanqueUpdateComponent } from 'app/entities/tanque/tanque-update.component';
import { TanqueService } from 'app/entities/tanque/tanque.service';
import { Tanque } from 'app/shared/model/tanque.model';

describe('Component Tests', () => {
  describe('Tanque Management Update Component', () => {
    let comp: TanqueUpdateComponent;
    let fixture: ComponentFixture<TanqueUpdateComponent>;
    let service: TanqueService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CraftBeerStoreTestModule],
        declarations: [TanqueUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TanqueUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TanqueUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TanqueService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Tanque(123);
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
        const entity = new Tanque();
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
