/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { CraftBeerStoreTestModule } from '../../../test.module';
import { MovimientosUpdateComponent } from 'app/entities/movimientos/movimientos-update.component';
import { MovimientosService } from 'app/entities/movimientos/movimientos.service';
import { Movimientos } from 'app/shared/model/movimientos.model';

describe('Component Tests', () => {
    describe('Movimientos Management Update Component', () => {
        let comp: MovimientosUpdateComponent;
        let fixture: ComponentFixture<MovimientosUpdateComponent>;
        let service: MovimientosService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CraftBeerStoreTestModule],
                declarations: [MovimientosUpdateComponent]
            })
                .overrideTemplate(MovimientosUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(MovimientosUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MovimientosService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Movimientos(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.movimientos = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Movimientos();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.movimientos = entity;
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
