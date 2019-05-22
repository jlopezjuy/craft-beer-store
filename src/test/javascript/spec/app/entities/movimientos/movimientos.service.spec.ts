/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { MovimientosService } from 'app/entities/movimientos/movimientos.service';
import { IMovimientos, Movimientos, TipoMovimiento, EstadoMovimiento } from 'app/shared/model/movimientos.model';

describe('Service Tests', () => {
    describe('Movimientos Service', () => {
        let injector: TestBed;
        let service: MovimientosService;
        let httpMock: HttpTestingController;
        let elemDefault: IMovimientos;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(MovimientosService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new Movimientos(0, TipoMovimiento.PRESUPUESTO, currentDate, 0, 'AAAAAAA', EstadoMovimiento.ACTIVO, 0);
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        fechaMovimiento: currentDate.format(DATE_FORMAT)
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

            it('should create a Movimientos', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        fechaMovimiento: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        fechaMovimiento: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new Movimientos(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a Movimientos', async () => {
                const returnedFromService = Object.assign(
                    {
                        tipoMovimiento: 'BBBBBB',
                        fechaMovimiento: currentDate.format(DATE_FORMAT),
                        precioTotal: 1,
                        numeroMovimiento: 'BBBBBB',
                        estado: 'BBBBBB',
                        litrosTotales: 1
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        fechaMovimiento: currentDate
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

            it('should return a list of Movimientos', async () => {
                const returnedFromService = Object.assign(
                    {
                        tipoMovimiento: 'BBBBBB',
                        fechaMovimiento: currentDate.format(DATE_FORMAT),
                        precioTotal: 1,
                        numeroMovimiento: 'BBBBBB',
                        estado: 'BBBBBB',
                        litrosTotales: 1
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        fechaMovimiento: currentDate
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

            it('should delete a Movimientos', async () => {
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
