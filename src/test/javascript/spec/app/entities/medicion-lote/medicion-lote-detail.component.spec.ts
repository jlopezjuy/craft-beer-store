import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CraftBeerStoreTestModule } from '../../../test.module';
import { MedicionLoteDetailComponent } from 'app/entities/medicion-lote/medicion-lote-detail.component';
import { MedicionLote } from 'app/shared/model/medicion-lote.model';

describe('Component Tests', () => {
  describe('MedicionLote Management Detail Component', () => {
    let comp: MedicionLoteDetailComponent;
    let fixture: ComponentFixture<MedicionLoteDetailComponent>;
    const route = ({ data: of({ medicionLote: new MedicionLote(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CraftBeerStoreTestModule],
        declarations: [MedicionLoteDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(MedicionLoteDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MedicionLoteDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.medicionLote).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
