import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IMedicionLote, TipoMedicion } from 'app/shared/model/medicion-lote.model';

type EntityResponseType = HttpResponse<IMedicionLote>;
type EntityArrayResponseType = HttpResponse<IMedicionLote[]>;

@Injectable({ providedIn: 'root' })
export class MedicionLoteService {
  public resourceUrl = SERVER_API_URL + 'api/medicion-lotes';

  constructor(protected http: HttpClient) {}

  create(medicionLote: IMedicionLote): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(medicionLote);
    return this.http
      .post<IMedicionLote>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(medicionLote: IMedicionLote): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(medicionLote);
    return this.http
      .put<IMedicionLote>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IMedicionLote>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IMedicionLote[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  queryLote(req?: any, loteId?: number): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IMedicionLote[]>(`${this.resourceUrl}/lote/${loteId}`, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  queryLoteTipo(req?: any, loteId?: number, tipoMedicion?: TipoMedicion): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IMedicionLote[]>(`${this.resourceUrl}/lote/${loteId}/tipo/${tipoMedicion}`, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(medicionLote: IMedicionLote): IMedicionLote {
    const copy: IMedicionLote = Object.assign({}, medicionLote, {
      fechaRealizado:
        medicionLote.fechaRealizado != null && medicionLote.fechaRealizado.isValid() ? medicionLote.fechaRealizado.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fechaRealizado = res.body.fechaRealizado != null ? moment(res.body.fechaRealizado) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((medicionLote: IMedicionLote) => {
        medicionLote.fechaRealizado = medicionLote.fechaRealizado != null ? moment(medicionLote.fechaRealizado) : null;
      });
    }
    return res;
  }
}
