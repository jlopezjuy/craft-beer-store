import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { CraftBeerStoreTestModule } from '../../../test.module';
import { PuntoDeVentaUpdateComponent } from 'app/entities/punto-de-venta/punto-de-venta-update.component';
import { PuntoDeVentaService } from 'app/entities/punto-de-venta/punto-de-venta.service';
import { PuntoDeVenta } from 'app/shared/model/punto-de-venta.model';

describe('Component Tests', () => {
  describe('PuntoDeVenta Management Update Component', () => {
    let comp: PuntoDeVentaUpdateComponent;
    let fixture: ComponentFixture<PuntoDeVentaUpdateComponent>;
    let service: PuntoDeVentaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CraftBeerStoreTestModule],
        declarations: [PuntoDeVentaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PuntoDeVentaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PuntoDeVentaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PuntoDeVentaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PuntoDeVenta(123);
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
        const entity = new PuntoDeVenta();
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
