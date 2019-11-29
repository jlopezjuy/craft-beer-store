import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { CraftBeerStoreTestModule } from '../../../test.module';
import { CompraInsumoUpdateComponent } from 'app/entities/compra-insumo/compra-insumo-update.component';
import { CompraInsumoService } from 'app/entities/compra-insumo/compra-insumo.service';
import { CompraInsumo } from 'app/shared/model/compra-insumo.model';

describe('Component Tests', () => {
  describe('CompraInsumo Management Update Component', () => {
    let comp: CompraInsumoUpdateComponent;
    let fixture: ComponentFixture<CompraInsumoUpdateComponent>;
    let service: CompraInsumoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CraftBeerStoreTestModule],
        declarations: [CompraInsumoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CompraInsumoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CompraInsumoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CompraInsumoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CompraInsumo(123);
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
        const entity = new CompraInsumo();
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
