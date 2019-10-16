/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CraftBeerStoreTestModule } from '../../../test.module';
import { MovimientoTanqueDetailComponent } from 'app/entities/movimiento-tanque/movimiento-tanque-detail.component';
import { MovimientoTanque } from 'app/shared/model/movimiento-tanque.model';

describe('Component Tests', () => {
  describe('MovimientoTanque Management Detail Component', () => {
    let comp: MovimientoTanqueDetailComponent;
    let fixture: ComponentFixture<MovimientoTanqueDetailComponent>;
    const route = ({ data: of({ movimientoTanque: new MovimientoTanque(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CraftBeerStoreTestModule],
        declarations: [MovimientoTanqueDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(MovimientoTanqueDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MovimientoTanqueDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.movimientoTanque).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
