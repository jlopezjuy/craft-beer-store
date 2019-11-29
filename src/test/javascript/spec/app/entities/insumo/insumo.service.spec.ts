import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import { InsumoService } from 'app/entities/insumo/insumo.service';
import { IInsumo, Insumo } from 'app/shared/model/insumo.model';
import { Unidad } from 'app/shared/model/enumerations/unidad.model';
import { TipoInsumo } from 'app/shared/model/enumerations/tipo-insumo.model';

describe('Service Tests', () => {
  describe('Insumo Service', () => {
    let injector: TestBed;
    let service: InsumoService;
    let httpMock: HttpTestingController;
    let elemDefault: IInsumo;
    let expectedResult;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(InsumoService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Insumo(0, 'AAAAAAA', 'AAAAAAA', 0, Unidad.KILOGRAMO, TipoInsumo.MALTA, 'image/png', 'AAAAAAA', 0, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a Insumo', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .create(new Insumo(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Insumo', () => {
        const returnedFromService = Object.assign(
          {
            nombreInsumo: 'BBBBBB',
            marca: 'BBBBBB',
            stock: 1,
            unidad: 'BBBBBB',
            tipo: 'BBBBBB',
            imagen: 'BBBBBB',
            precioUnitario: 1,
            precioTotal: 1
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of Insumo', () => {
        const returnedFromService = Object.assign(
          {
            nombreInsumo: 'BBBBBB',
            marca: 'BBBBBB',
            stock: 1,
            unidad: 'BBBBBB',
            tipo: 'BBBBBB',
            imagen: 'BBBBBB',
            precioUnitario: 1,
            precioTotal: 1
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .query(expected)
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Insumo', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
