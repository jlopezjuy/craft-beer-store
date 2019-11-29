import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { CraftBeerStoreTestModule } from '../../../test.module';
import { CompraInsumoDetalleUpdateComponent } from 'app/entities/compra-insumo-detalle/compra-insumo-detalle-update.component';
import { CompraInsumoDetalleService } from 'app/entities/compra-insumo-detalle/compra-insumo-detalle.service';
import { CompraInsumoDetalle } from 'app/shared/model/compra-insumo-detalle.model';

describe('Component Tests', () => {
  describe('CompraInsumoDetalle Management Update Component', () => {
    let comp: CompraInsumoDetalleUpdateComponent;
    let fixture: ComponentFixture<CompraInsumoDetalleUpdateComponent>;
    let service: CompraInsumoDetalleService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CraftBeerStoreTestModule],
        declarations: [CompraInsumoDetalleUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CompraInsumoDetalleUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CompraInsumoDetalleUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CompraInsumoDetalleService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CompraInsumoDetalle(123);
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
        const entity = new CompraInsumoDetalle();
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
