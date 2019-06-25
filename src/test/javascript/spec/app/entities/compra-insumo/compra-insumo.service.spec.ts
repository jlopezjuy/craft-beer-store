/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { CompraInsumoService } from 'app/entities/compra-insumo/compra-insumo.service';
import { ICompraInsumo, CompraInsumo } from 'app/shared/model/compra-insumo.model';

describe('Service Tests', () => {
  describe('CompraInsumo Service', () => {
    let injector: TestBed;
    let service: CompraInsumoService;
    let httpMock: HttpTestingController;
    let elemDefault: ICompraInsumo;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      injector = getTestBed();
      service = injector.get(CompraInsumoService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new CompraInsumo(0, 'AAAAAAA', currentDate, 0, 0, 0, 0);
    });

    describe('Service methods', async () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            fecha: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(JSON.stringify(returnedFromService));
      });

      it('should create a CompraInsumo', async () => {
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
          .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(JSON.stringify(returnedFromService));
      });

      it('should update a CompraInsumo', async () => {
        const returnedFromService = Object.assign(
          {
            nroFactura: 'BBBBBB',
            fecha: currentDate.format(DATE_FORMAT),
            subtotal: 1,
            gastoDeEnvio: 1,
            impuesto: 1,
            total: 1
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
          .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(JSON.stringify(returnedFromService));
      });

      it('should return a list of CompraInsumo', async () => {
        const returnedFromService = Object.assign(
          {
            nroFactura: 'BBBBBB',
            fecha: currentDate.format(DATE_FORMAT),
            subtotal: 1,
            gastoDeEnvio: 1,
            impuesto: 1,
            total: 1
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
          .subscribe(body => expect(body).toContainEqual(expected));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(JSON.stringify([returnedFromService]));
        httpMock.verify();
      });

      it('should delete a CompraInsumo', async () => {
        const rxPromise = service.delete(123).subscribe(resp => expect(resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
