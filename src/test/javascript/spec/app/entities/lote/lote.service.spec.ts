import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { LoteService } from 'app/entities/lote/lote.service';
import { ILote, Lote } from 'app/shared/model/lote.model';
import { EstadoLote } from 'app/shared/model/enumerations/estado-lote.model';

describe('Service Tests', () => {
  describe('Lote Service', () => {
    let injector: TestBed;
    let service: LoteService;
    let httpMock: HttpTestingController;
    let elemDefault: ILote;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(LoteService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Lote(0, 'AAAAAAA', currentDate, 0, 'AAAAAAA', false, EstadoLote.EN_PROCESO, 0, 0, 0, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            fechaCoccion: currentDate.format(DATE_FORMAT)
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

      it('should create a Lote', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            fechaCoccion: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            fechaCoccion: currentDate
          },
          returnedFromService
        );
        service
          .create(new Lote(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Lote', () => {
        const returnedFromService = Object.assign(
          {
            codigo: 'BBBBBB',
            fechaCoccion: currentDate.format(DATE_FORMAT),
            coccion: 1,
            descripcion: 'BBBBBB',
            descuentaStock: true,
            estado: 'BBBBBB',
            litrosEstimados: 1,
            litrosEnTanque: 1,
            litrosEnvasados: 1,
            litrosDisponible: 1
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fechaCoccion: currentDate
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

      it('should return a list of Lote', () => {
        const returnedFromService = Object.assign(
          {
            codigo: 'BBBBBB',
            fechaCoccion: currentDate.format(DATE_FORMAT),
            coccion: 1,
            descripcion: 'BBBBBB',
            descuentaStock: true,
            estado: 'BBBBBB',
            litrosEstimados: 1,
            litrosEnTanque: 1,
            litrosEnvasados: 1,
            litrosDisponible: 1
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            fechaCoccion: currentDate
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

      it('should delete a Lote', () => {
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
