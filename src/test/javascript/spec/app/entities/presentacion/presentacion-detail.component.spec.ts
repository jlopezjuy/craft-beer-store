/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CraftBeerStoreTestModule } from '../../../test.module';
import { PresentacionDetailComponent } from 'app/entities/presentacion/presentacion-detail.component';
import { Presentacion } from 'app/shared/model/presentacion.model';

describe('Component Tests', () => {
  describe('Presentacion Management Detail Component', () => {
    let comp: PresentacionDetailComponent;
    let fixture: ComponentFixture<PresentacionDetailComponent>;
    const route = ({ data: of({ presentacion: new Presentacion(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CraftBeerStoreTestModule],
        declarations: [PresentacionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PresentacionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PresentacionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.presentacion).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
