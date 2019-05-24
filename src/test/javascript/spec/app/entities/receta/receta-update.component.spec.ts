/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { CraftBeerStoreTestModule } from '../../../test.module';
import { RecetaUpdateComponent } from 'app/entities/receta/receta-update.component';
import { RecetaService } from 'app/entities/receta/receta.service';
import { Receta } from 'app/shared/model/receta.model';

describe('Component Tests', () => {
    describe('Receta Management Update Component', () => {
        let comp: RecetaUpdateComponent;
        let fixture: ComponentFixture<RecetaUpdateComponent>;
        let service: RecetaService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CraftBeerStoreTestModule],
                declarations: [RecetaUpdateComponent]
            })
                .overrideTemplate(RecetaUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(RecetaUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RecetaService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Receta(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.receta = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Receta();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.receta = entity;
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
