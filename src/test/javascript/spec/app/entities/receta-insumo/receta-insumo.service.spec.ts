import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import { RecetaInsumoService } from 'app/entities/receta-insumo/receta-insumo.service';
import { IRecetaInsumo, RecetaInsumo } from 'app/shared/model/receta-insumo.model';
import { TipoInsumo } from 'app/shared/model/enumerations/tipo-insumo.model';
import { UsoMalta } from 'app/shared/model/enumerations/uso-malta.model';
import { ModoLupulo } from 'app/shared/model/enumerations/modo-lupulo.model';
import { UsoLupulo } from 'app/shared/model/enumerations/uso-lupulo.model';
import { TipoOtro } from 'app/shared/model/enumerations/tipo-otro.model';
import { UsoOtro } from 'app/shared/model/enumerations/uso-otro.model';

describe('Service Tests', () => {
  describe('RecetaInsumo Service', () => {
    let injector: TestBed;
    let service: RecetaInsumoService;
    let httpMock: HttpTestingController;
    let elemDefault: IRecetaInsumo;
    let expectedResult;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
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

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a RecetaInsumo', () => {
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
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a RecetaInsumo', () => {
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
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of RecetaInsumo', () => {
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
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a RecetaInsumo', () => {
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
