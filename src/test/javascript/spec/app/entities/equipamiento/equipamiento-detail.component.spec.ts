import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CraftBeerStoreTestModule } from '../../../test.module';
import { EquipamientoDetailComponent } from 'app/entities/equipamiento/equipamiento-detail.component';
import { Equipamiento } from 'app/shared/model/equipamiento.model';

describe('Component Tests', () => {
  describe('Equipamiento Management Detail Component', () => {
    let comp: EquipamientoDetailComponent;
    let fixture: ComponentFixture<EquipamientoDetailComponent>;
    const route = ({ data: of({ equipamiento: new Equipamiento(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CraftBeerStoreTestModule],
        declarations: [EquipamientoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(EquipamientoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EquipamientoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.equipamiento).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
