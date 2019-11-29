import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CraftBeerStoreTestModule } from '../../../test.module';
import { BarrilDetailComponent } from 'app/entities/barril/barril-detail.component';
import { Barril } from 'app/shared/model/barril.model';

describe('Component Tests', () => {
  describe('Barril Management Detail Component', () => {
    let comp: BarrilDetailComponent;
    let fixture: ComponentFixture<BarrilDetailComponent>;
    const route = ({ data: of({ barril: new Barril(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CraftBeerStoreTestModule],
        declarations: [BarrilDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(BarrilDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BarrilDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.barril).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
