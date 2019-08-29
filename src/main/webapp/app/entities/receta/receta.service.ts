import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IReceta } from 'app/shared/model/receta.model';

type EntityResponseType = HttpResponse<IReceta>;
type EntityArrayResponseType = HttpResponse<IReceta[]>;

@Injectable({ providedIn: 'root' })
export class RecetaService {
  public resourceUrl = SERVER_API_URL + 'api/recetas';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/recetas';

  constructor(protected http: HttpClient) {}

  create(receta: IReceta): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(receta);
    return this.http
      .post<IReceta>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(receta: IReceta): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(receta);
    return this.http
      .put<IReceta>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IReceta>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  queryByProducto(req?: any, productoId?: number): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IReceta[]>(`${this.resourceUrl}/producto/${productoId}`, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  findAllByProducto(req?: any, productoId?: number): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IReceta[]>(`${this.resourceUrl}/producto/list/${productoId}`, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IReceta[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IReceta[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(receta: IReceta): IReceta {
    const copy: IReceta = Object.assign({}, receta, {
      fecha: receta.fecha != null && receta.fecha.isValid() ? receta.fecha.format(DATE_FORMAT) : null
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
      res.body.forEach((receta: IReceta) => {
        receta.fecha = receta.fecha != null ? moment(receta.fecha) : null;
      });
    }
    return res;
  }
}
