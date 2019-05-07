/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import { EstilosService } from 'app/entities/estilos/estilos.service';
import { IEstilos, Estilos } from 'app/shared/model/estilos.model';

describe('Service Tests', () => {
    describe('Estilos Service', () => {
        let injector: TestBed;
        let service: EstilosService;
        let httpMock: HttpTestingController;
        let elemDefault: IEstilos;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(EstilosService);
            httpMock = injector.get(HttpTestingController);

            elemDefault = new Estilos(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 0, 0, 0, 0, 0, 0, 0, 0, 0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA');
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign({}, elemDefault);
                service
                    .find(123)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a Estilos', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0
                    },
                    elemDefault
                );
                const expected = Object.assign({}, returnedFromService);
                service
                    .create(new Estilos(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a Estilos', async () => {
                const returnedFromService = Object.assign(
                    {
                        number: 'BBBBBB',
                        nombreEstilo: 'BBBBBB',
                        categoriaEstilo: 'BBBBBB',
                        abvMin: 1,
                        abvMax: 1,
                        abvMed: 1,
                        ibuMin: 1,
                        ibuMax: 1,
                        ibuMed: 1,
                        srmMin: 1,
                        srmMax: 1,
                        srmMed: 1,
                        descripcion: 'BBBBBB',
                        ejemploNombreComercial: 'BBBBBB',
                        ejemploImagenComercial: 'BBBBBB'
                    },
                    elemDefault
                );

                const expected = Object.assign({}, returnedFromService);
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of Estilos', async () => {
                const returnedFromService = Object.assign(
                    {
                        number: 'BBBBBB',
                        nombreEstilo: 'BBBBBB',
                        categoriaEstilo: 'BBBBBB',
                        abvMin: 1,
                        abvMax: 1,
                        abvMed: 1,
                        ibuMin: 1,
                        ibuMax: 1,
                        ibuMed: 1,
                        srmMin: 1,
                        srmMax: 1,
                        srmMed: 1,
                        descripcion: 'BBBBBB',
                        ejemploNombreComercial: 'BBBBBB',
                        ejemploImagenComercial: 'BBBBBB'
                    },
                    elemDefault
                );
                const expected = Object.assign({}, returnedFromService);
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

            it('should delete a Estilos', async () => {
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
