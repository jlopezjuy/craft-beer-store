/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { CraftBeerStoreTestModule } from '../../../test.module';
import { InsumoRecomendadoUpdateComponent } from 'app/entities/insumo-recomendado/insumo-recomendado-update.component';
import { InsumoRecomendadoService } from 'app/entities/insumo-recomendado/insumo-recomendado.service';
import { InsumoRecomendado } from 'app/shared/model/insumo-recomendado.model';

describe('Component Tests', () => {
    describe('InsumoRecomendado Management Update Component', () => {
        let comp: InsumoRecomendadoUpdateComponent;
        let fixture: ComponentFixture<InsumoRecomendadoUpdateComponent>;
        let service: InsumoRecomendadoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CraftBeerStoreTestModule],
                declarations: [InsumoRecomendadoUpdateComponent]
            })
                .overrideTemplate(InsumoRecomendadoUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(InsumoRecomendadoUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(InsumoRecomendadoService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new InsumoRecomendado(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.insumoRecomendado = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new InsumoRecomendado();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.insumoRecomendado = entity;
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
