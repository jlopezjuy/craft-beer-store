import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ILote } from 'app/shared/model/lote.model';

type EntityResponseType = HttpResponse<ILote>;
type EntityArrayResponseType = HttpResponse<ILote[]>;

@Injectable({ providedIn: 'root' })
export class LoteService {
  public resourceUrl = SERVER_API_URL + 'api/lotes';

  constructor(protected http: HttpClient) {}

  create(lote: ILote): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(lote);
    return this.http
      .post<ILote>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(lote: ILote): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(lote);
    return this.http
      .put<ILote>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ILote>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ILote[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  queryByEmpresa(req?: any, empresaId?: number): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ILote[]>(`${this.resourceUrl}/empresa/${empresaId}`, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(lote: ILote): ILote {
    const copy: ILote = Object.assign({}, lote, {
      fechaCoccion: lote.fechaCoccion != null && lote.fechaCoccion.isValid() ? lote.fechaCoccion.format(DATE_FORMAT) : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fechaCoccion = res.body.fechaCoccion != null ? moment(res.body.fechaCoccion) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((lote: ILote) => {
        lote.fechaCoccion = lote.fechaCoccion != null ? moment(lote.fechaCoccion) : null;
      });
    }
    return res;
  }
}
