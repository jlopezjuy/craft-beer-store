import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { EquipamientoService } from 'app/entities/equipamiento/equipamiento.service';
import { IEquipamiento, Equipamiento } from 'app/shared/model/equipamiento.model';
import { TipoEquipamiento } from 'app/shared/model/enumerations/tipo-equipamiento.model';

describe('Service Tests', () => {
  describe('Equipamiento Service', () => {
    let injector: TestBed;
    let service: EquipamientoService;
    let httpMock: HttpTestingController;
    let elemDefault: IEquipamiento;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(EquipamientoService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Equipamiento(0, 'AAAAAAA', TipoEquipamiento.MEDICION, 0, 0, currentDate, 'image/png', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            fechaCompra: currentDate.format(DATE_FORMAT)
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

      it('should create a Equipamiento', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            fechaCompra: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            fechaCompra: currentDate
          },
          returnedFromService
        );
        service
          .create(new Equipamiento(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Equipamiento', () => {
        const returnedFromService = Object.assign(
          {
            nombreEquipamiento: 'BBBBBB',
            tipoEquipamiento: 'BBBBBB',
            precio: 1,
            costoEnvio: 1,
            fechaCompra: currentDate.format(DATE_FORMAT),
            imagen: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fechaCompra: currentDate
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

      it('should return a list of Equipamiento', () => {
        const returnedFromService = Object.assign(
          {
            nombreEquipamiento: 'BBBBBB',
            tipoEquipamiento: 'BBBBBB',
            precio: 1,
            costoEnvio: 1,
            fechaCompra: currentDate.format(DATE_FORMAT),
            imagen: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            fechaCompra: currentDate
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

      it('should delete a Equipamiento', () => {
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
