import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CraftBeerStoreTestModule } from '../../../test.module';
import { MovimientoBarrilDetailComponent } from 'app/entities/movimiento-barril/movimiento-barril-detail.component';
import { MovimientoBarril } from 'app/shared/model/movimiento-barril.model';

describe('Component Tests', () => {
  describe('MovimientoBarril Management Detail Component', () => {
    let comp: MovimientoBarrilDetailComponent;
    let fixture: ComponentFixture<MovimientoBarrilDetailComponent>;
    const route = ({ data: of({ movimientoBarril: new MovimientoBarril(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CraftBeerStoreTestModule],
        declarations: [MovimientoBarrilDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(MovimientoBarrilDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MovimientoBarrilDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.movimientoBarril).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
