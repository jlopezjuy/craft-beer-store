/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { CraftBeerStoreTestModule } from '../../../test.module';
import { MedicionLoteUpdateComponent } from 'app/entities/medicion-lote/medicion-lote-update.component';
import { MedicionLoteService } from 'app/entities/medicion-lote/medicion-lote.service';
import { MedicionLote } from 'app/shared/model/medicion-lote.model';

describe('Component Tests', () => {
  describe('MedicionLote Management Update Component', () => {
    let comp: MedicionLoteUpdateComponent;
    let fixture: ComponentFixture<MedicionLoteUpdateComponent>;
    let service: MedicionLoteService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CraftBeerStoreTestModule],
        declarations: [MedicionLoteUpdateComponent]
      })
        .overrideTemplate(MedicionLoteUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MedicionLoteUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MedicionLoteService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new MedicionLote(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.medicionLote = entity;
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new MedicionLote();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.medicionLote = entity;
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
