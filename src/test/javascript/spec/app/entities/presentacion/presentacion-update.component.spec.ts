/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { CraftBeerStoreTestModule } from '../../../test.module';
import { PresentacionUpdateComponent } from 'app/entities/presentacion/presentacion-update.component';
import { PresentacionService } from 'app/entities/presentacion/presentacion.service';
import { Presentacion } from 'app/shared/model/presentacion.model';

describe('Component Tests', () => {
    describe('Presentacion Management Update Component', () => {
        let comp: PresentacionUpdateComponent;
        let fixture: ComponentFixture<PresentacionUpdateComponent>;
        let service: PresentacionService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CraftBeerStoreTestModule],
                declarations: [PresentacionUpdateComponent]
            })
                .overrideTemplate(PresentacionUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PresentacionUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PresentacionService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Presentacion(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.presentacion = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Presentacion();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.presentacion = entity;
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
