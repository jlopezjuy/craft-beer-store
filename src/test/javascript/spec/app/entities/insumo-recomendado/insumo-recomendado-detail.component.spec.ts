/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CraftBeerStoreTestModule } from '../../../test.module';
import { InsumoRecomendadoDetailComponent } from 'app/entities/insumo-recomendado/insumo-recomendado-detail.component';
import { InsumoRecomendado } from 'app/shared/model/insumo-recomendado.model';

describe('Component Tests', () => {
    describe('InsumoRecomendado Management Detail Component', () => {
        let comp: InsumoRecomendadoDetailComponent;
        let fixture: ComponentFixture<InsumoRecomendadoDetailComponent>;
        const route = ({ data: of({ insumoRecomendado: new InsumoRecomendado(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CraftBeerStoreTestModule],
                declarations: [InsumoRecomendadoDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(InsumoRecomendadoDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(InsumoRecomendadoDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.insumoRecomendado).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
