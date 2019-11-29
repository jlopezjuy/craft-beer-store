import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IMovimientoTanque } from 'app/shared/model/movimiento-tanque.model';

type EntityResponseType = HttpResponse<IMovimientoTanque>;
type EntityArrayResponseType = HttpResponse<IMovimientoTanque[]>;

@Injectable({ providedIn: 'root' })
export class MovimientoTanqueService {
  public resourceUrl = SERVER_API_URL + 'api/movimiento-tanques';

  constructor(protected http: HttpClient) {}

  create(movimientoTanque: IMovimientoTanque): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(movimientoTanque);
    return this.http
      .post<IMovimientoTanque>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(movimientoTanque: IMovimientoTanque): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(movimientoTanque);
    return this.http
      .put<IMovimientoTanque>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IMovimientoTanque>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IMovimientoTanque[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(movimientoTanque: IMovimientoTanque): IMovimientoTanque {
    const copy: IMovimientoTanque = Object.assign({}, movimientoTanque, {
      fecha: movimientoTanque.fecha != null && movimientoTanque.fecha.isValid() ? movimientoTanque.fecha.format(DATE_FORMAT) : null
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
      res.body.forEach((movimientoTanque: IMovimientoTanque) => {
        movimientoTanque.fecha = movimientoTanque.fecha != null ? moment(movimientoTanque.fecha) : null;
      });
    }
    return res;
  }
}
