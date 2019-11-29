import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { TanqueService } from 'app/entities/tanque/tanque.service';
import { ITanque, Tanque } from 'app/shared/model/tanque.model';
import { TipoTanque } from 'app/shared/model/enumerations/tipo-tanque.model';
import { EstadoTanque } from 'app/shared/model/enumerations/estado-tanque.model';

describe('Service Tests', () => {
  describe('Tanque Service', () => {
    let injector: TestBed;
    let service: TanqueService;
    let httpMock: HttpTestingController;
    let elemDefault: ITanque;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(TanqueService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Tanque(0, 'AAAAAAA', 0, TipoTanque.UNITANK_INOX, EstadoTanque.VACIO, 0, currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            fechaIngreso: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a Tanque', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            fechaIngreso: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            fechaIngreso: currentDate
          },
          returnedFromService
        );
        service
          .create(new Tanque(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Tanque', () => {
        const returnedFromService = Object.assign(
          {
            nombre: 'BBBBBB',
            litros: 1,
            tipo: 'BBBBBB',
            estado: 'BBBBBB',
            listrosDisponible: 1,
            fechaIngreso: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fechaIngreso: currentDate
          },
          returnedFromService
        );
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of Tanque', () => {
        const returnedFromService = Object.assign(
          {
            nombre: 'BBBBBB',
            litros: 1,
            tipo: 'BBBBBB',
            estado: 'BBBBBB',
            listrosDisponible: 1,
            fechaIngreso: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            fechaIngreso: currentDate
          },
          returnedFromService
        );
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

      it('should delete a Tanque', () => {
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
