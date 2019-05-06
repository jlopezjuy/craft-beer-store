/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { CraftBeerStoreTestModule } from '../../../test.module';
import { EventoProductoUpdateComponent } from 'app/entities/evento-producto/evento-producto-update.component';
import { EventoProductoService } from 'app/entities/evento-producto/evento-producto.service';
import { EventoProducto } from 'app/shared/model/evento-producto.model';

describe('Component Tests', () => {
    describe('EventoProducto Management Update Component', () => {
        let comp: EventoProductoUpdateComponent;
        let fixture: ComponentFixture<EventoProductoUpdateComponent>;
        let service: EventoProductoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CraftBeerStoreTestModule],
                declarations: [EventoProductoUpdateComponent]
            })
                .overrideTemplate(EventoProductoUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(EventoProductoUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EventoProductoService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new EventoProducto(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.eventoProducto = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new EventoProducto();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.eventoProducto = entity;
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
