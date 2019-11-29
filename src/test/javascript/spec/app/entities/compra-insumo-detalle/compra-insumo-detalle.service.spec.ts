import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import { CompraInsumoDetalleService } from 'app/entities/compra-insumo-detalle/compra-insumo-detalle.service';
import { ICompraInsumoDetalle, CompraInsumoDetalle } from 'app/shared/model/compra-insumo-detalle.model';
import { Unidad } from 'app/shared/model/enumerations/unidad.model';
import { TipoInsumo } from 'app/shared/model/enumerations/tipo-insumo.model';

describe('Service Tests', () => {
  describe('CompraInsumoDetalle Service', () => {
    let injector: TestBed;
    let service: CompraInsumoDetalleService;
    let httpMock: HttpTestingController;
    let elemDefault: ICompraInsumoDetalle;
    let expectedResult;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(CompraInsumoDetalleService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new CompraInsumoDetalle(0, Unidad.KILOGRAMO, 'AAAAAAA', 0, 0, 0, TipoInsumo.MALTA);
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

      it('should create a CompraInsumoDetalle', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .create(new CompraInsumoDetalle(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a CompraInsumoDetalle', () => {
        const returnedFromService = Object.assign(
          {
            unidad: 'BBBBBB',
            codigoReferencia: 'BBBBBB',
            stock: 1,
            precioUnitario: 1,
            precioTotal: 1,
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

      it('should return a list of CompraInsumoDetalle', () => {
        const returnedFromService = Object.assign(
          {
            unidad: 'BBBBBB',
            codigoReferencia: 'BBBBBB',
            stock: 1,
            precioUnitario: 1,
            precioTotal: 1,
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

      it('should delete a CompraInsumoDetalle', () => {
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
