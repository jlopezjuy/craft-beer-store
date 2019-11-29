import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { CraftBeerStoreTestModule } from '../../../test.module';
import { EtapaLoteUpdateComponent } from 'app/entities/etapa-lote/etapa-lote-update.component';
import { EtapaLoteService } from 'app/entities/etapa-lote/etapa-lote.service';
import { EtapaLote } from 'app/shared/model/etapa-lote.model';

describe('Component Tests', () => {
  describe('EtapaLote Management Update Component', () => {
    let comp: EtapaLoteUpdateComponent;
    let fixture: ComponentFixture<EtapaLoteUpdateComponent>;
    let service: EtapaLoteService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CraftBeerStoreTestModule],
        declarations: [EtapaLoteUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(EtapaLoteUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EtapaLoteUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EtapaLoteService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new EtapaLote(123);
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
        const entity = new EtapaLote();
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
