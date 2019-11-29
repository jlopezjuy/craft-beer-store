import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IMovimientos } from 'app/shared/model/movimientos.model';

type EntityResponseType = HttpResponse<IMovimientos>;
type EntityArrayResponseType = HttpResponse<IMovimientos[]>;

@Injectable({ providedIn: 'root' })
export class MovimientosService {
  public resourceUrl = SERVER_API_URL + 'api/movimientos';

  constructor(protected http: HttpClient) {}

  create(movimientos: IMovimientos): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(movimientos);
    return this.http
      .post<IMovimientos>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(movimientos: IMovimientos): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(movimientos);
    return this.http
      .put<IMovimientos>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IMovimientos>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IMovimientos[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(movimientos: IMovimientos): IMovimientos {
    const copy: IMovimientos = Object.assign({}, movimientos, {
      fechaMovimiento:
        movimientos.fechaMovimiento != null && movimientos.fechaMovimiento.isValid()
          ? movimientos.fechaMovimiento.format(DATE_FORMAT)
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
      res.body.forEach((movimientos: IMovimientos) => {
        movimientos.fechaMovimiento = movimientos.fechaMovimiento != null ? moment(movimientos.fechaMovimiento) : null;
      });
    }
    return res;
  }
}
