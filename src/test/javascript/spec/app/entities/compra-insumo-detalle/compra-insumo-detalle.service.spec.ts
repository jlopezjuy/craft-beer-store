/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import { CompraInsumoDetalleService } from 'app/entities/compra-insumo-detalle/compra-insumo-detalle.service';
import { ICompraInsumoDetalle, CompraInsumoDetalle, Unidad, TipoInsumo } from 'app/shared/model/compra-insumo-detalle.model';

describe('Service Tests', () => {
  describe('CompraInsumoDetalle Service', () => {
    let injector: TestBed;
    let service: CompraInsumoDetalleService;
    let httpMock: HttpTestingController;
    let elemDefault: ICompraInsumoDetalle;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      injector = getTestBed();
      service = injector.get(CompraInsumoDetalleService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new CompraInsumoDetalle(0, Unidad.KILOGRAMO, 'AAAAAAA', 0, 0, TipoInsumo.MALTA);
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

      it('should create a CompraInsumoDetalle', async () => {
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
          .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(JSON.stringify(returnedFromService));
      });

      it('should update a CompraInsumoDetalle', async () => {
        const returnedFromService = Object.assign(
          {
            unidad: 'BBBBBB',
            codigoReferencia: 'BBBBBB',
            stock: 1,
            precio: 1,
            tipo: 'BBBBBB'
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

      it('should return a list of CompraInsumoDetalle', async () => {
        const returnedFromService = Object.assign(
          {
            unidad: 'BBBBBB',
            codigoReferencia: 'BBBBBB',
            stock: 1,
            precio: 1,
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
          .subscribe(body => expect(body).toContainEqual(expected));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(JSON.stringify([returnedFromService]));
        httpMock.verify();
      });

      it('should delete a CompraInsumoDetalle', async () => {
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
