/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CraftBeerStoreTestModule } from '../../../test.module';
import { EventoProductoDetailComponent } from 'app/entities/evento-producto/evento-producto-detail.component';
import { EventoProducto } from 'app/shared/model/evento-producto.model';

describe('Component Tests', () => {
    describe('EventoProducto Management Detail Component', () => {
        let comp: EventoProductoDetailComponent;
        let fixture: ComponentFixture<EventoProductoDetailComponent>;
        const route = ({ data: of({ eventoProducto: new EventoProducto(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CraftBeerStoreTestModule],
                declarations: [EventoProductoDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(EventoProductoDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(EventoProductoDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.eventoProducto).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
