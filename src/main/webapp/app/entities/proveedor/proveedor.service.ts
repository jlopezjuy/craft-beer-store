import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IProveedor } from 'app/shared/model/proveedor.model';

type EntityResponseType = HttpResponse<IProveedor>;
type EntityArrayResponseType = HttpResponse<IProveedor[]>;

@Injectable({ providedIn: 'root' })
export class ProveedorService {
  public resourceUrl = SERVER_API_URL + 'api/proveedors';

  constructor(protected http: HttpClient) {}

  create(proveedor: IProveedor): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(proveedor);
    return this.http
      .post<IProveedor>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(proveedor: IProveedor): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(proveedor);
    return this.http
      .put<IProveedor>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IProveedor>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IProveedor[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  queryByEmpresa(req?: any, empresaId?: number): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IProveedor[]>(`${this.resourceUrl}/empresa/${empresaId}`, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(proveedor: IProveedor): IProveedor {
    const copy: IProveedor = Object.assign({}, proveedor, {
      fechaAlta: proveedor.fechaAlta != null && proveedor.fechaAlta.isValid() ? proveedor.fechaAlta.format(DATE_FORMAT) : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fechaAlta = res.body.fechaAlta != null ? moment(res.body.fechaAlta) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((proveedor: IProveedor) => {
        proveedor.fechaAlta = proveedor.fechaAlta != null ? moment(proveedor.fechaAlta) : null;
      });
    }
    return res;
  }
}
