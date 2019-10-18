import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IEtapaLote } from 'app/shared/model/etapa-lote.model';
import { ILote } from '../../shared/model/lote.model';

type EntityResponseType = HttpResponse<IEtapaLote>;
type EntityArrayResponseType = HttpResponse<IEtapaLote[]>;

@Injectable({ providedIn: 'root' })
export class EtapaLoteService {
  public resourceUrl = SERVER_API_URL + 'api/etapa-lotes';

  constructor(protected http: HttpClient) {}

  create(etapaLote: IEtapaLote): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(etapaLote);
    return this.http
      .post<IEtapaLote>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(etapaLote: IEtapaLote): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(etapaLote);
    return this.http
      .put<IEtapaLote>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IEtapaLote>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  findTop(loteId: number): Observable<EntityResponseType> {
    return this.http
      .get<ILote>(`${this.resourceUrl}/lote/top/${loteId}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IEtapaLote[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  queryByLote(req?: any, loteId?: number): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IEtapaLote[]>(`${this.resourceUrl}/lote/${loteId}`, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(etapaLote: IEtapaLote): IEtapaLote {
    const copy: IEtapaLote = Object.assign({}, etapaLote, {
      inicio: etapaLote.inicio != null && etapaLote.inicio.isValid() ? etapaLote.inicio.format(DATE_FORMAT) : null,
      fin: etapaLote.fin != null && etapaLote.fin.isValid() ? etapaLote.fin.format(DATE_FORMAT) : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.inicio = res.body.inicio != null ? moment(res.body.inicio) : null;
      res.body.fin = res.body.fin != null ? moment(res.body.fin) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((etapaLote: IEtapaLote) => {
        etapaLote.inicio = etapaLote.inicio != null ? moment(etapaLote.inicio) : null;
        etapaLote.fin = etapaLote.fin != null ? moment(etapaLote.fin) : null;
      });
    }
    return res;
  }
}
