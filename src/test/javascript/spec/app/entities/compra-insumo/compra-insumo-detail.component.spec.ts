import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CraftBeerStoreTestModule } from '../../../test.module';
import { CompraInsumoDetailComponent } from 'app/entities/compra-insumo/compra-insumo-detail.component';
import { CompraInsumo } from 'app/shared/model/compra-insumo.model';

describe('Component Tests', () => {
  describe('CompraInsumo Management Detail Component', () => {
    let comp: CompraInsumoDetailComponent;
    let fixture: ComponentFixture<CompraInsumoDetailComponent>;
    const route = ({ data: of({ compraInsumo: new CompraInsumo(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CraftBeerStoreTestModule],
        declarations: [CompraInsumoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CompraInsumoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CompraInsumoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.compraInsumo).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
