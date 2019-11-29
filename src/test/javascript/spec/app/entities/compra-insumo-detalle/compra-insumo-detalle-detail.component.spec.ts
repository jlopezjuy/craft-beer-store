import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CraftBeerStoreTestModule } from '../../../test.module';
import { CompraInsumoDetalleDetailComponent } from 'app/entities/compra-insumo-detalle/compra-insumo-detalle-detail.component';
import { CompraInsumoDetalle } from 'app/shared/model/compra-insumo-detalle.model';

describe('Component Tests', () => {
  describe('CompraInsumoDetalle Management Detail Component', () => {
    let comp: CompraInsumoDetalleDetailComponent;
    let fixture: ComponentFixture<CompraInsumoDetalleDetailComponent>;
    const route = ({ data: of({ compraInsumoDetalle: new CompraInsumoDetalle(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CraftBeerStoreTestModule],
        declarations: [CompraInsumoDetalleDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CompraInsumoDetalleDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CompraInsumoDetalleDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.compraInsumoDetalle).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
