import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { MovimientoBarrilService } from 'app/entities/movimiento-barril/movimiento-barril.service';
import { IMovimientoBarril, MovimientoBarril } from 'app/shared/model/movimiento-barril.model';
import { EstadoMovimientoBarril } from 'app/shared/model/enumerations/estado-movimiento-barril.model';

describe('Service Tests', () => {
  describe('MovimientoBarril Service', () => {
    let injector: TestBed;
    let service: MovimientoBarrilService;
    let httpMock: HttpTestingController;
    let elemDefault: IMovimientoBarril;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(MovimientoBarrilService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new MovimientoBarril(0, currentDate, EstadoMovimientoBarril.VACIO, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            fechaMovimiento: currentDate.format(DATE_FORMAT)
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

      it('should create a MovimientoBarril', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            fechaMovimiento: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            fechaMovimiento: currentDate
          },
          returnedFromService
        );
        service
          .create(new MovimientoBarril(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a MovimientoBarril', () => {
        const returnedFromService = Object.assign(
          {
            fechaMovimiento: currentDate.format(DATE_FORMAT),
            estado: 'BBBBBB',
            dias: 1
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fechaMovimiento: currentDate
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

      it('should return a list of MovimientoBarril', () => {
        const returnedFromService = Object.assign(
          {
            fechaMovimiento: currentDate.format(DATE_FORMAT),
            estado: 'BBBBBB',
            dias: 1
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            fechaMovimiento: currentDate
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

      it('should delete a MovimientoBarril', () => {
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
