/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { TanqueService } from 'app/entities/tanque/tanque.service';
import { ITanque, Tanque, TipoTanque, EstadoTanque } from 'app/shared/model/tanque.model';

describe('Service Tests', () => {
  describe('Tanque Service', () => {
    let injector: TestBed;
    let service: TanqueService;
    let httpMock: HttpTestingController;
    let elemDefault: ITanque;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      injector = getTestBed();
      service = injector.get(TanqueService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Tanque(0, 'AAAAAAA', 0, TipoTanque.UNITANK_INOX, EstadoTanque.VACIO, 0, currentDate);
    });

    describe('Service methods', async () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            fechaIngreso: currentDate.format(DATE_FORMAT)
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

      it('should create a Tanque', async () => {
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
          .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(JSON.stringify(returnedFromService));
      });

      it('should update a Tanque', async () => {
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
          .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(JSON.stringify(returnedFromService));
      });

      it('should return a list of Tanque', async () => {
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
          .subscribe(body => expect(body).toContainEqual(expected));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(JSON.stringify([returnedFromService]));
        httpMock.verify();
      });

      it('should delete a Tanque', async () => {
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
