/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { EventoService } from 'app/entities/evento/evento.service';
import { IEvento, Evento } from 'app/shared/model/evento.model';

describe('Service Tests', () => {
    describe('Evento Service', () => {
        let injector: TestBed;
        let service: EventoService;
        let httpMock: HttpTestingController;
        let elemDefault: IEvento;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(EventoService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new Evento(0, 'AAAAAAA', currentDate, 0, 0);
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        fechaEvento: currentDate.format(DATE_FORMAT)
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

            it('should create a Evento', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        fechaEvento: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        fechaEvento: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new Evento(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a Evento', async () => {
                const returnedFromService = Object.assign(
                    {
                        nombreEvento: 'BBBBBB',
                        fechaEvento: currentDate.format(DATE_FORMAT),
                        cantidadBarriles: 1,
                        precioPinta: 1
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        fechaEvento: currentDate
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

            it('should return a list of Evento', async () => {
                const returnedFromService = Object.assign(
                    {
                        nombreEvento: 'BBBBBB',
                        fechaEvento: currentDate.format(DATE_FORMAT),
                        cantidadBarriles: 1,
                        precioPinta: 1
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        fechaEvento: currentDate
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

            it('should delete a Evento', async () => {
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
