/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { ProveedorService } from 'app/entities/proveedor/proveedor.service';
import { IProveedor, Proveedor, CondicionFiscal, Provincia } from 'app/shared/model/proveedor.model';

describe('Service Tests', () => {
    describe('Proveedor Service', () => {
        let injector: TestBed;
        let service: ProveedorService;
        let httpMock: HttpTestingController;
        let elemDefault: IProveedor;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
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

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        fechaAlta: currentDate.format(DATE_FORMAT)
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

            it('should create a Proveedor', async () => {
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
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a Proveedor', async () => {
                const returnedFromService = Object.assign(
                    {
                        nombreProveedor: 'BBBBBB',
                        razonSocial: 'BBBBBB',
                        cuit: 'BBBBBB',
                        telefono: 'BBBBBB',
                        fechaAlta: currentDate.format(DATE_FORMAT),
                        domicilio: 'BBBBBB',
                        email: 'BBBBBB',
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
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of Proveedor', async () => {
                const returnedFromService = Object.assign(
                    {
                        nombreProveedor: 'BBBBBB',
                        razonSocial: 'BBBBBB',
                        cuit: 'BBBBBB',
                        telefono: 'BBBBBB',
                        fechaAlta: currentDate.format(DATE_FORMAT),
                        domicilio: 'BBBBBB',
                        email: 'BBBBBB',
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
                    .subscribe(body => expect(body).toContainEqual(expected));
                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify([returnedFromService]));
                httpMock.verify();
            });

            it('should delete a Proveedor', async () => {
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
