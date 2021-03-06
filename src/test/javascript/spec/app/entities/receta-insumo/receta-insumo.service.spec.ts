/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import { RecetaInsumoService } from 'app/entities/receta-insumo/receta-insumo.service';
import { IRecetaInsumo, RecetaInsumo, UsoMalta, ModoLupulo, UsoLupulo, TipoOtro, UsoOtro } from 'app/shared/model/receta-insumo.model';
import { TipoInsumo } from 'app/shared/model/insumo.model';

describe('Service Tests', () => {
    describe('RecetaInsumo Service', () => {
        let injector: TestBed;
        let service: RecetaInsumoService;
        let httpMock: HttpTestingController;
        let elemDefault: IRecetaInsumo;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(RecetaInsumoService);
            httpMock = injector.get(HttpTestingController);

            elemDefault = new RecetaInsumo(
                0,
                TipoInsumo.MALTA,
                0,
                0,
                0,
                UsoMalta.MASH,
                0,
                ModoLupulo.PELLET,
                0,
                UsoLupulo.BOIL,
                0,
                0,
                0,
                0,
                0,
                TipoOtro.FINING,
                UsoOtro.BOIL,
                0
            );
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

            it('should create a RecetaInsumo', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0
                    },
                    elemDefault
                );
                const expected = Object.assign({}, returnedFromService);
                service
                    .create(new RecetaInsumo(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a RecetaInsumo', async () => {
                const returnedFromService = Object.assign(
                    {
                        tipoInsumo: 'BBBBBB',
                        cantidad: 1,
                        color: 1,
                        porcentaje: 1,
                        usoMalta: 'BBBBBB',
                        alpha: 1,
                        modoLupulo: 'BBBBBB',
                        gramos: 1,
                        usoLupulo: 'BBBBBB',
                        tiempo: 1,
                        ibu: 1,
                        densidadLeva: 1,
                        tamSobre: 1,
                        atenuacion: 1,
                        tipoOtro: 'BBBBBB',
                        usoOtro: 'BBBBBB',
                        tiempoOtro: 1
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

            it('should return a list of RecetaInsumo', async () => {
                const returnedFromService = Object.assign(
                    {
                        tipoInsumo: 'BBBBBB',
                        cantidad: 1,
                        color: 1,
                        porcentaje: 1,
                        usoMalta: 'BBBBBB',
                        alpha: 1,
                        modoLupulo: 'BBBBBB',
                        gramos: 1,
                        usoLupulo: 'BBBBBB',
                        tiempo: 1,
                        ibu: 1,
                        densidadLeva: 1,
                        tamSobre: 1,
                        atenuacion: 1,
                        tipoOtro: 'BBBBBB',
                        usoOtro: 'BBBBBB',
                        tiempoOtro: 1
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

            it('should delete a RecetaInsumo', async () => {
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
