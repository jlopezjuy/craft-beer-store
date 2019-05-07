/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { CraftBeerStoreTestModule } from '../../../test.module';
import { EstilosUpdateComponent } from 'app/entities/estilos/estilos-update.component';
import { EstilosService } from 'app/entities/estilos/estilos.service';
import { Estilos } from 'app/shared/model/estilos.model';

describe('Component Tests', () => {
    describe('Estilos Management Update Component', () => {
        let comp: EstilosUpdateComponent;
        let fixture: ComponentFixture<EstilosUpdateComponent>;
        let service: EstilosService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CraftBeerStoreTestModule],
                declarations: [EstilosUpdateComponent]
            })
                .overrideTemplate(EstilosUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(EstilosUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EstilosService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Estilos(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.estilos = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Estilos();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.estilos = entity;
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
