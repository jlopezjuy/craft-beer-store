/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CraftBeerStoreTestModule } from '../../../test.module';
import { TanqueDetailComponent } from 'app/entities/tanque/tanque-detail.component';
import { Tanque } from 'app/shared/model/tanque.model';

describe('Component Tests', () => {
  describe('Tanque Management Detail Component', () => {
    let comp: TanqueDetailComponent;
    let fixture: ComponentFixture<TanqueDetailComponent>;
    const route = ({ data: of({ tanque: new Tanque(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CraftBeerStoreTestModule],
        declarations: [TanqueDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TanqueDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TanqueDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.tanque).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
