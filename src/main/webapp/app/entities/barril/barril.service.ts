import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IBarril } from 'app/shared/model/barril.model';

type EntityResponseType = HttpResponse<IBarril>;
type EntityArrayResponseType = HttpResponse<IBarril[]>;

@Injectable({ providedIn: 'root' })
export class BarrilService {
  public resourceUrl = SERVER_API_URL + 'api/barrils';

  constructor(protected http: HttpClient) {}

  create(barril: IBarril): Observable<EntityResponseType> {
    return this.http.post<IBarril>(this.resourceUrl, barril, { observe: 'response' });
  }

  update(barril: IBarril): Observable<EntityResponseType> {
    return this.http.put<IBarril>(this.resourceUrl, barril, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IBarril>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBarril[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
