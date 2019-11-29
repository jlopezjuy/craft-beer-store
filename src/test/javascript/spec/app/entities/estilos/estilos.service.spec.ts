import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import { EstilosService } from 'app/entities/estilos/estilos.service';
import { IEstilos, Estilos } from 'app/shared/model/estilos.model';

describe('Service Tests', () => {
  describe('Estilos Service', () => {
    let injector: TestBed;
    let service: EstilosService;
    let httpMock: HttpTestingController;
    let elemDefault: IEstilos;
    let expectedResult;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(EstilosService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Estilos(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 0, 0, 0, 0, 0, 0, 0, 0, 0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA');
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

      it('should create a Estilos', () => {
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
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Estilos', () => {
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
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of Estilos', () => {
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
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Estilos', () => {
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
