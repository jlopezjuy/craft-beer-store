/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { CraftBeerStoreTestModule } from '../../../test.module';
import { EquipamientoUpdateComponent } from 'app/entities/equipamiento/equipamiento-update.component';
import { EquipamientoService } from 'app/entities/equipamiento/equipamiento.service';
import { Equipamiento } from 'app/shared/model/equipamiento.model';

describe('Component Tests', () => {
    describe('Equipamiento Management Update Component', () => {
        let comp: EquipamientoUpdateComponent;
        let fixture: ComponentFixture<EquipamientoUpdateComponent>;
        let service: EquipamientoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CraftBeerStoreTestModule],
                declarations: [EquipamientoUpdateComponent]
            })
                .overrideTemplate(EquipamientoUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(EquipamientoUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EquipamientoService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Equipamiento(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.equipamiento = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Equipamiento();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.equipamiento = entity;
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
