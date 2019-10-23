import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { EstadoTanque, ITanque } from 'app/shared/model/tanque.model';

type EntityResponseType = HttpResponse<ITanque>;
type EntityArrayResponseType = HttpResponse<ITanque[]>;

@Injectable({ providedIn: 'root' })
export class TanqueService {
  public resourceUrl = SERVER_API_URL + 'api/tanques';

  constructor(protected http: HttpClient) {}

  create(tanque: ITanque): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tanque);
    return this.http
      .post<ITanque>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(tanque: ITanque): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tanque);
    return this.http
      .put<ITanque>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITanque>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITanque[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  queryByEmpresa(req?: any, empresaId?: number): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITanque[]>(`${this.resourceUrl}/empresa/${empresaId}`, { params: options, observe: 'response' });
  }

  queryByEmpresaEstadoVacio(req?: any, empresaId?: number, estadoTanque?: EstadoTanque): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITanque[]>(`${this.resourceUrl}/empresa/${empresaId}/${estadoTanque}`, { params: options, observe: 'response' });
  }

  queryByEmpresaLote(req?: any, empresaId?: number, loteId?: number): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITanque[]>(`${this.resourceUrl}/empresa/${empresaId}/lote/${loteId}`, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(tanque: ITanque): ITanque {
    const copy: ITanque = Object.assign({}, tanque, {
      fechaIngreso: tanque.fechaIngreso != null && tanque.fechaIngreso.isValid() ? tanque.fechaIngreso.format(DATE_FORMAT) : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fechaIngreso = res.body.fechaIngreso != null ? moment(res.body.fechaIngreso) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((tanque: ITanque) => {
        tanque.fechaIngreso = tanque.fechaIngreso != null ? moment(tanque.fechaIngreso) : null;
      });
    }
    return res;
  }
}
