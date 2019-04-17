/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CraftBeerStoreTestModule } from '../../../test.module';
import { MovimientosDetailComponent } from 'app/entities/movimientos/movimientos-detail.component';
import { Movimientos } from 'app/shared/model/movimientos.model';

describe('Component Tests', () => {
    describe('Movimientos Management Detail Component', () => {
        let comp: MovimientosDetailComponent;
        let fixture: ComponentFixture<MovimientosDetailComponent>;
        const route = ({ data: of({ movimientos: new Movimientos(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CraftBeerStoreTestModule],
                declarations: [MovimientosDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(MovimientosDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(MovimientosDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.movimientos).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
