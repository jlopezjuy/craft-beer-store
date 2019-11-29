import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { MovimientosService } from 'app/entities/movimientos/movimientos.service';
import { IMovimientos, Movimientos } from 'app/shared/model/movimientos.model';
import { TipoMovimiento } from 'app/shared/model/enumerations/tipo-movimiento.model';
import { EstadoMovimiento } from 'app/shared/model/enumerations/estado-movimiento.model';

describe('Service Tests', () => {
  describe('Movimientos Service', () => {
    let injector: TestBed;
    let service: MovimientosService;
    let httpMock: HttpTestingController;
    let elemDefault: IMovimientos;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(MovimientosService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Movimientos(0, TipoMovimiento.PRESUPUESTO, currentDate, 0, 'AAAAAAA', EstadoMovimiento.ACTIVO, 0);
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

      it('should create a Movimientos', () => {
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
          .create(new Movimientos(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Movimientos', () => {
        const returnedFromService = Object.assign(
          {
            tipoMovimiento: 'BBBBBB',
            fechaMovimiento: currentDate.format(DATE_FORMAT),
            precioTotal: 1,
            numeroMovimiento: 'BBBBBB',
            estado: 'BBBBBB',
            litrosTotales: 1
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

      it('should return a list of Movimientos', () => {
        const returnedFromService = Object.assign(
          {
            tipoMovimiento: 'BBBBBB',
            fechaMovimiento: currentDate.format(DATE_FORMAT),
            precioTotal: 1,
            numeroMovimiento: 'BBBBBB',
            estado: 'BBBBBB',
            litrosTotales: 1
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

      it('should delete a Movimientos', () => {
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
