import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { CompraInsumoService } from 'app/entities/compra-insumo/compra-insumo.service';
import { ICompraInsumo, CompraInsumo } from 'app/shared/model/compra-insumo.model';
import { EstadoCompra } from 'app/shared/model/enumerations/estado-compra.model';

describe('Service Tests', () => {
  describe('CompraInsumo Service', () => {
    let injector: TestBed;
    let service: CompraInsumoService;
    let httpMock: HttpTestingController;
    let elemDefault: ICompraInsumo;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(CompraInsumoService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new CompraInsumo(0, 'AAAAAAA', currentDate, 0, 0, 0, 0, EstadoCompra.PEDIDO_REALIZADO);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            fecha: currentDate.format(DATE_FORMAT)
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

      it('should create a CompraInsumo', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            fecha: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            fecha: currentDate
          },
          returnedFromService
        );
        service
          .create(new CompraInsumo(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a CompraInsumo', () => {
        const returnedFromService = Object.assign(
          {
            nroFactura: 'BBBBBB',
            fecha: currentDate.format(DATE_FORMAT),
            subtotal: 1,
            gastoDeEnvio: 1,
            impuesto: 1,
            total: 1,
            estadoCompra: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fecha: currentDate
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

      it('should return a list of CompraInsumo', () => {
        const returnedFromService = Object.assign(
          {
            nroFactura: 'BBBBBB',
            fecha: currentDate.format(DATE_FORMAT),
            subtotal: 1,
            gastoDeEnvio: 1,
            impuesto: 1,
            total: 1,
            estadoCompra: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            fecha: currentDate
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

      it('should delete a CompraInsumo', () => {
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
