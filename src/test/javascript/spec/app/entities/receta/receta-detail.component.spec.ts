/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CraftBeerStoreTestModule } from '../../../test.module';
import { RecetaDetailComponent } from 'app/entities/receta/receta-detail.component';
import { Receta } from 'app/shared/model/receta.model';

describe('Component Tests', () => {
    describe('Receta Management Detail Component', () => {
        let comp: RecetaDetailComponent;
        let fixture: ComponentFixture<RecetaDetailComponent>;
        const route = ({ data: of({ receta: new Receta(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CraftBeerStoreTestModule],
                declarations: [RecetaDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(RecetaDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RecetaDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.receta).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
