import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { CraftBeerStoreTestModule } from '../../../test.module';
import { RecetaInsumoUpdateComponent } from 'app/entities/receta-insumo/receta-insumo-update.component';
import { RecetaInsumoService } from 'app/entities/receta-insumo/receta-insumo.service';
import { RecetaInsumo } from 'app/shared/model/receta-insumo.model';

describe('Component Tests', () => {
  describe('RecetaInsumo Management Update Component', () => {
    let comp: RecetaInsumoUpdateComponent;
    let fixture: ComponentFixture<RecetaInsumoUpdateComponent>;
    let service: RecetaInsumoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CraftBeerStoreTestModule],
        declarations: [RecetaInsumoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(RecetaInsumoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RecetaInsumoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RecetaInsumoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new RecetaInsumo(123);
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
        const entity = new RecetaInsumo();
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
