/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { MedicionLoteService } from 'app/entities/medicion-lote/medicion-lote.service';
import { IMedicionLote, MedicionLote, TipoMedicion } from 'app/shared/model/medicion-lote.model';

describe('Service Tests', () => {
  describe('MedicionLote Service', () => {
    let injector: TestBed;
    let service: MedicionLoteService;
    let httpMock: HttpTestingController;
    let elemDefault: IMedicionLote;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      injector = getTestBed();
      service = injector.get(MedicionLoteService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new MedicionLote(0, 0, TipoMedicion.DENSIDAD, 'AAAAAAA', currentDate, 0, 'AAAAAAA');
    });

    describe('Service methods', async () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            fechaRealizado: currentDate.format(DATE_TIME_FORMAT)
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

      it('should create a MedicionLote', async () => {
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
          .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(JSON.stringify(returnedFromService));
      });

      it('should update a MedicionLote', async () => {
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
          .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(JSON.stringify(returnedFromService));
      });

      it('should return a list of MedicionLote', async () => {
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
          .subscribe(body => expect(body).toContainEqual(expected));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(JSON.stringify([returnedFromService]));
        httpMock.verify();
      });

      it('should delete a MedicionLote', async () => {
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
