import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IMovimientoBarril } from 'app/shared/model/movimiento-barril.model';

type EntityResponseType = HttpResponse<IMovimientoBarril>;
type EntityArrayResponseType = HttpResponse<IMovimientoBarril[]>;

@Injectable({ providedIn: 'root' })
export class MovimientoBarrilService {
  public resourceUrl = SERVER_API_URL + 'api/movimiento-barrils';

  constructor(protected http: HttpClient) {}

  create(movimientoBarril: IMovimientoBarril): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(movimientoBarril);
    return this.http
      .post<IMovimientoBarril>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(movimientoBarril: IMovimientoBarril): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(movimientoBarril);
    return this.http
      .put<IMovimientoBarril>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IMovimientoBarril>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IMovimientoBarril[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  queryByBarril(req?: any, barrilId?: number): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IMovimientoBarril[]>(`${this.resourceUrl}/barril/${barrilId}`, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(movimientoBarril: IMovimientoBarril): IMovimientoBarril {
    const copy: IMovimientoBarril = Object.assign({}, movimientoBarril, {
      fechaMovimiento:
        movimientoBarril.fechaMovimiento != null && movimientoBarril.fechaMovimiento.isValid()
          ? movimientoBarril.fechaMovimiento.format(DATE_FORMAT)
          : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fechaMovimiento = res.body.fechaMovimiento != null ? moment(res.body.fechaMovimiento) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((movimientoBarril: IMovimientoBarril) => {
        movimientoBarril.fechaMovimiento = movimientoBarril.fechaMovimiento != null ? moment(movimientoBarril.fechaMovimiento) : null;
      });
    }
    return res;
  }
}
