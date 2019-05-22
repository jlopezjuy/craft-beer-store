/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CraftBeerStoreTestModule } from '../../../test.module';
import { EstilosDetailComponent } from 'app/entities/estilos/estilos-detail.component';
import { Estilos } from 'app/shared/model/estilos.model';

describe('Component Tests', () => {
    describe('Estilos Management Detail Component', () => {
        let comp: EstilosDetailComponent;
        let fixture: ComponentFixture<EstilosDetailComponent>;
        const route = ({ data: of({ estilos: new Estilos(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CraftBeerStoreTestModule],
                declarations: [EstilosDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(EstilosDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(EstilosDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.estilos).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
