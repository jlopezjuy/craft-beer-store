import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEstilos } from 'app/shared/model/estilos.model';

type EntityResponseType = HttpResponse<IEstilos>;
type EntityArrayResponseType = HttpResponse<IEstilos[]>;

@Injectable({ providedIn: 'root' })
export class EstilosService {
  public resourceUrl = SERVER_API_URL + 'api/estilos';

  constructor(protected http: HttpClient) {}

  create(estilos: IEstilos): Observable<EntityResponseType> {
    return this.http.post<IEstilos>(this.resourceUrl, estilos, { observe: 'response' });
  }

  update(estilos: IEstilos): Observable<EntityResponseType> {
    return this.http.put<IEstilos>(this.resourceUrl, estilos, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEstilos>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEstilos[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
