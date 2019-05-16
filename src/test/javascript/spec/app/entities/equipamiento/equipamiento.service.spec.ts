/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { EquipamientoService } from 'app/entities/equipamiento/equipamiento.service';
import { IEquipamiento, Equipamiento, TipoEquipamiento } from 'app/shared/model/equipamiento.model';

describe('Service Tests', () => {
    describe('Equipamiento Service', () => {
        let injector: TestBed;
        let service: EquipamientoService;
        let httpMock: HttpTestingController;
        let elemDefault: IEquipamiento;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(EquipamientoService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new Equipamiento(0, 'AAAAAAA', TipoEquipamiento.MEDICION, 0, 0, currentDate, 'image/png', 'AAAAAAA');
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        fechaCompra: currentDate.format(DATE_FORMAT)
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

            it('should create a Equipamiento', async () => {
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
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a Equipamiento', async () => {
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
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of Equipamiento', async () => {
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
                    .subscribe(body => expect(body).toContainEqual(expected));
                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify([returnedFromService]));
                httpMock.verify();
            });

            it('should delete a Equipamiento', async () => {
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
