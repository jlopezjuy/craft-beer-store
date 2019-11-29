import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CraftBeerStoreTestModule } from '../../../test.module';
import { RecetaInsumoDetailComponent } from 'app/entities/receta-insumo/receta-insumo-detail.component';
import { RecetaInsumo } from 'app/shared/model/receta-insumo.model';

describe('Component Tests', () => {
  describe('RecetaInsumo Management Detail Component', () => {
    let comp: RecetaInsumoDetailComponent;
    let fixture: ComponentFixture<RecetaInsumoDetailComponent>;
    const route = ({ data: of({ recetaInsumo: new RecetaInsumo(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CraftBeerStoreTestModule],
        declarations: [RecetaInsumoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(RecetaInsumoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RecetaInsumoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.recetaInsumo).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
