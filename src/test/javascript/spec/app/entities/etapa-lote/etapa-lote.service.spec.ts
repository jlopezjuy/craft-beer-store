/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { EtapaLoteService } from 'app/entities/etapa-lote/etapa-lote.service';
import { IEtapaLote, EtapaLote, EtapaLoteEnum } from 'app/shared/model/etapa-lote.model';

describe('Service Tests', () => {
  describe('EtapaLote Service', () => {
    let injector: TestBed;
    let service: EtapaLoteService;
    let httpMock: HttpTestingController;
    let elemDefault: IEtapaLote;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      injector = getTestBed();
      service = injector.get(EtapaLoteService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new EtapaLote(0, EtapaLoteEnum.COCCION, 0, currentDate, currentDate, 0);
    });

    describe('Service methods', async () => {
      it('should find an element', async () => {
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
          .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(JSON.stringify(returnedFromService));
      });

      it('should create a EtapaLote', async () => {
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
          .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(JSON.stringify(returnedFromService));
      });

      it('should update a EtapaLote', async () => {
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
          .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(JSON.stringify(returnedFromService));
      });

      it('should return a list of EtapaLote', async () => {
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
          .subscribe(body => expect(body).toContainEqual(expected));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(JSON.stringify([returnedFromService]));
        httpMock.verify();
      });

      it('should delete a EtapaLote', async () => {
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
