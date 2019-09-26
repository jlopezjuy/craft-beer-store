/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CraftBeerStoreTestModule } from '../../../test.module';
import { EtapaLoteDetailComponent } from 'app/entities/etapa-lote/etapa-lote-detail.component';
import { EtapaLote } from 'app/shared/model/etapa-lote.model';

describe('Component Tests', () => {
  describe('EtapaLote Management Detail Component', () => {
    let comp: EtapaLoteDetailComponent;
    let fixture: ComponentFixture<EtapaLoteDetailComponent>;
    const route = ({ data: of({ etapaLote: new EtapaLote(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CraftBeerStoreTestModule],
        declarations: [EtapaLoteDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(EtapaLoteDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EtapaLoteDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.etapaLote).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
