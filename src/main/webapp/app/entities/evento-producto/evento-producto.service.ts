import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEventoProducto } from 'app/shared/model/evento-producto.model';

type EntityResponseType = HttpResponse<IEventoProducto>;
type EntityArrayResponseType = HttpResponse<IEventoProducto[]>;

@Injectable({ providedIn: 'root' })
export class EventoProductoService {
  public resourceUrl = SERVER_API_URL + 'api/evento-productos';

  constructor(protected http: HttpClient) {}

  create(eventoProducto: IEventoProducto): Observable<EntityResponseType> {
    return this.http.post<IEventoProducto>(this.resourceUrl, eventoProducto, { observe: 'response' });
  }

  update(eventoProducto: IEventoProducto): Observable<EntityResponseType> {
    return this.http.put<IEventoProducto>(this.resourceUrl, eventoProducto, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEventoProducto>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEventoProducto[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
