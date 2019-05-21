/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CraftBeerStoreTestModule } from '../../../test.module';
import { PuntoDeVentaDetailComponent } from 'app/entities/punto-de-venta/punto-de-venta-detail.component';
import { PuntoDeVenta } from 'app/shared/model/punto-de-venta.model';

describe('Component Tests', () => {
    describe('PuntoDeVenta Management Detail Component', () => {
        let comp: PuntoDeVentaDetailComponent;
        let fixture: ComponentFixture<PuntoDeVentaDetailComponent>;
        const route = ({ data: of({ puntoDeVenta: new PuntoDeVenta(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CraftBeerStoreTestModule],
                declarations: [PuntoDeVentaDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(PuntoDeVentaDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PuntoDeVentaDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.puntoDeVenta).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
