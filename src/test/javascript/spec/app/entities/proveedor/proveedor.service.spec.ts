import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { ProveedorService } from 'app/entities/proveedor/proveedor.service';
import { IProveedor, Proveedor } from 'app/shared/model/proveedor.model';
import { CondicionFiscal } from 'app/shared/model/enumerations/condicion-fiscal.model';
import { Provincia } from 'app/shared/model/enumerations/provincia.model';

describe('Service Tests', () => {
  describe('Proveedor Service', () => {
    let injector: TestBed;
    let service: ProveedorService;
    let httpMock: HttpTestingController;
    let elemDefault: IProveedor;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(ProveedorService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Proveedor(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        CondicionFiscal.RESPONSABLE_INSCRIPTO,
        'AAAAAAA',
        0,
        Provincia.MISIONES,
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            fechaAlta: currentDate.format(DATE_FORMAT)
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

      it('should create a Proveedor', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            fechaAlta: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            fechaAlta: currentDate
          },
          returnedFromService
        );
        service
          .create(new Proveedor(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Proveedor', () => {
        const returnedFromService = Object.assign(
          {
            nombreProveedor: 'BBBBBB',
            razonSocial: 'BBBBBB',
            cuit: 'BBBBBB',
            telefono: 'BBBBBB',
            fechaAlta: currentDate.format(DATE_FORMAT),
            domicilio: 'BBBBBB',
            correo: 'BBBBBB',
            notas: 'BBBBBB',
            condicionFiscal: 'BBBBBB',
            localidad: 'BBBBBB',
            codigoPostal: 1,
            provincia: 'BBBBBB',
            contacto: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fechaAlta: currentDate
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

      it('should return a list of Proveedor', () => {
        const returnedFromService = Object.assign(
          {
            nombreProveedor: 'BBBBBB',
            razonSocial: 'BBBBBB',
            cuit: 'BBBBBB',
            telefono: 'BBBBBB',
            fechaAlta: currentDate.format(DATE_FORMAT),
            domicilio: 'BBBBBB',
            correo: 'BBBBBB',
            notas: 'BBBBBB',
            condicionFiscal: 'BBBBBB',
            localidad: 'BBBBBB',
            codigoPostal: 1,
            provincia: 'BBBBBB',
            contacto: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            fechaAlta: currentDate
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

      it('should delete a Proveedor', () => {
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
