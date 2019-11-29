import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import { InsumoRecomendadoService } from 'app/entities/insumo-recomendado/insumo-recomendado.service';
import { IInsumoRecomendado, InsumoRecomendado } from 'app/shared/model/insumo-recomendado.model';
import { TipoInsumo } from 'app/shared/model/enumerations/tipo-insumo.model';

describe('Service Tests', () => {
  describe('InsumoRecomendado Service', () => {
    let injector: TestBed;
    let service: InsumoRecomendadoService;
    let httpMock: HttpTestingController;
    let elemDefault: IInsumoRecomendado;
    let expectedResult;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(InsumoRecomendadoService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new InsumoRecomendado(0, 'AAAAAAA', 'AAAAAAA', TipoInsumo.MALTA);
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

      it('should create a InsumoRecomendado', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .create(new InsumoRecomendado(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a InsumoRecomendado', () => {
        const returnedFromService = Object.assign(
          {
            nombre: 'BBBBBB',
            marca: 'BBBBBB',
            tipo: 'BBBBBB'
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

      it('should return a list of InsumoRecomendado', () => {
        const returnedFromService = Object.assign(
          {
            nombre: 'BBBBBB',
            marca: 'BBBBBB',
            tipo: 'BBBBBB'
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

      it('should delete a InsumoRecomendado', () => {
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
