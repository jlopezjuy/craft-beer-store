import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import { BarrilService } from 'app/entities/barril/barril.service';
import { IBarril, Barril } from 'app/shared/model/barril.model';
import { LitrosBarril } from 'app/shared/model/enumerations/litros-barril.model';
import { ConectorBarril } from 'app/shared/model/enumerations/conector-barril.model';
import { EstadoBarril } from 'app/shared/model/enumerations/estado-barril.model';

describe('Service Tests', () => {
  describe('Barril Service', () => {
    let injector: TestBed;
    let service: BarrilService;
    let httpMock: HttpTestingController;
    let elemDefault: IBarril;
    let expectedResult;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(BarrilService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Barril(0, 'AAAAAAA', LitrosBarril.CINCO, ConectorBarril.G, EstadoBarril.VACIO, 'image/png', 'AAAAAAA');
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

      it('should create a Barril', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .create(new Barril(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Barril', () => {
        const returnedFromService = Object.assign(
          {
            codigo: 'BBBBBB',
            litros: 'BBBBBB',
            conector: 'BBBBBB',
            estado: 'BBBBBB',
            imagen: 'BBBBBB'
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

      it('should return a list of Barril', () => {
        const returnedFromService = Object.assign(
          {
            codigo: 'BBBBBB',
            litros: 'BBBBBB',
            conector: 'BBBBBB',
            estado: 'BBBBBB',
            imagen: 'BBBBBB'
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

      it('should delete a Barril', () => {
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
