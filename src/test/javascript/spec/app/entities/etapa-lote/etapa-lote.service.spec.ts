import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { EtapaLoteService } from 'app/entities/etapa-lote/etapa-lote.service';
import { IEtapaLote, EtapaLote } from 'app/shared/model/etapa-lote.model';
import { EtapaLoteEnum } from 'app/shared/model/enumerations/etapa-lote-enum.model';

describe('Service Tests', () => {
  describe('EtapaLote Service', () => {
    let injector: TestBed;
    let service: EtapaLoteService;
    let httpMock: HttpTestingController;
    let elemDefault: IEtapaLote;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(EtapaLoteService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new EtapaLote(0, EtapaLoteEnum.COCCION, 0, currentDate, currentDate, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            inicio: currentDate.format(DATE_FORMAT),
            fin: currentDate.format(DATE_FORMAT)
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

      it('should create a EtapaLote', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            inicio: currentDate.format(DATE_FORMAT),
            fin: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            inicio: currentDate,
            fin: currentDate
          },
          returnedFromService
        );
        service
          .create(new EtapaLote(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a EtapaLote', () => {
        const returnedFromService = Object.assign(
          {
            etapa: 'BBBBBB',
            litros: 1,
            inicio: currentDate.format(DATE_FORMAT),
            fin: currentDate.format(DATE_FORMAT),
            dias: 1
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            inicio: currentDate,
            fin: currentDate
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

      it('should return a list of EtapaLote', () => {
        const returnedFromService = Object.assign(
          {
            etapa: 'BBBBBB',
            litros: 1,
            inicio: currentDate.format(DATE_FORMAT),
            fin: currentDate.format(DATE_FORMAT),
            dias: 1
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            inicio: currentDate,
            fin: currentDate
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

      it('should delete a EtapaLote', () => {
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
