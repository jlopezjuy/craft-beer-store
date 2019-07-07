import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IInsumoRecomendado } from 'app/shared/model/insumo-recomendado.model';
import { IInsumo, TipoInsumo } from '../../shared/model/insumo.model';

type EntityResponseType = HttpResponse<IInsumoRecomendado>;
type EntityArrayResponseType = HttpResponse<IInsumoRecomendado[]>;

@Injectable({ providedIn: 'root' })
export class InsumoRecomendadoService {
  public resourceUrl = SERVER_API_URL + 'api/insumo-recomendados';

  constructor(protected http: HttpClient) {}

  create(insumoRecomendado: IInsumoRecomendado): Observable<EntityResponseType> {
    return this.http.post<IInsumoRecomendado>(this.resourceUrl, insumoRecomendado, { observe: 'response' });
  }

  update(insumoRecomendado: IInsumoRecomendado): Observable<EntityResponseType> {
    return this.http.put<IInsumoRecomendado>(this.resourceUrl, insumoRecomendado, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IInsumoRecomendado>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IInsumoRecomendado[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  queryAll(): Observable<EntityArrayResponseType> {
    return this.http.get<IInsumoRecomendado[]>(this.resourceUrl + '/all', { observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  queryByEmpresaTipo(tipoInsumo: TipoInsumo): Observable<EntityArrayResponseType> {
    return this.http.get<IInsumo[]>(`${this.resourceUrl}/tipo/${tipoInsumo}`, { observe: 'response' });
  }

  queryByEmpresaNotInTipo(req: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IInsumo[]>(`${this.resourceUrl}/tipo`, { params: options, observe: 'response' });
  }
}
