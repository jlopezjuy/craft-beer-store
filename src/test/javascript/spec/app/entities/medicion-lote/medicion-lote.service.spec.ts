import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { MedicionLoteService } from 'app/entities/medicion-lote/medicion-lote.service';
import { IMedicionLote, MedicionLote } from 'app/shared/model/medicion-lote.model';
import { TipoMedicion } from 'app/shared/model/enumerations/tipo-medicion.model';

describe('Service Tests', () => {
  describe('MedicionLote Service', () => {
    let injector: TestBed;
    let service: MedicionLoteService;
    let httpMock: HttpTestingController;
    let elemDefault: IMedicionLote;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(MedicionLoteService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new MedicionLote(0, 0, TipoMedicion.DENSIDAD, 'AAAAAAA', currentDate, 0, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            fechaRealizado: currentDate.format(DATE_TIME_FORMAT)
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

      it('should create a MedicionLote', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            fechaRealizado: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            fechaRealizado: currentDate
          },
          returnedFromService
        );
        service
          .create(new MedicionLote(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a MedicionLote', () => {
        const returnedFromService = Object.assign(
          {
            dia: 1,
            tipoMedicion: 'BBBBBB',
            estado: 'BBBBBB',
            fechaRealizado: currentDate.format(DATE_TIME_FORMAT),
            valor: 1,
            observacion: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fechaRealizado: currentDate
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

      it('should return a list of MedicionLote', () => {
        const returnedFromService = Object.assign(
          {
            dia: 1,
            tipoMedicion: 'BBBBBB',
            estado: 'BBBBBB',
            fechaRealizado: currentDate.format(DATE_TIME_FORMAT),
            valor: 1,
            observacion: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            fechaRealizado: currentDate
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

      it('should delete a MedicionLote', () => {
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
