import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICompraInsumo } from 'app/shared/model/compra-insumo.model';

type EntityResponseType = HttpResponse<ICompraInsumo>;
type EntityArrayResponseType = HttpResponse<ICompraInsumo[]>;

@Injectable({ providedIn: 'root' })
export class CompraInsumoService {
  public resourceUrl = SERVER_API_URL + 'api/compra-insumos';

  constructor(protected http: HttpClient) {}

  create(compraInsumo: ICompraInsumo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(compraInsumo);
    return this.http
      .post<ICompraInsumo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(compraInsumo: ICompraInsumo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(compraInsumo);
    return this.http
      .put<ICompraInsumo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICompraInsumo>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICompraInsumo[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(compraInsumo: ICompraInsumo): ICompraInsumo {
    const copy: ICompraInsumo = Object.assign({}, compraInsumo, {
      fecha: compraInsumo.fecha != null && compraInsumo.fecha.isValid() ? compraInsumo.fecha.format(DATE_FORMAT) : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fecha = res.body.fecha != null ? moment(res.body.fecha) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((compraInsumo: ICompraInsumo) => {
        compraInsumo.fecha = compraInsumo.fecha != null ? moment(compraInsumo.fecha) : null;
      });
    }
    return res;
  }
}
