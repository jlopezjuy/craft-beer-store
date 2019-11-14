import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IInsumo, TipoInsumo } from 'app/shared/model/insumo.model';

type EntityResponseType = HttpResponse<IInsumo>;
type EntityArrayResponseType = HttpResponse<IInsumo[]>;

@Injectable({ providedIn: 'root' })
export class InsumoService {
  public resourceUrl = SERVER_API_URL + 'api/insumos';

  constructor(protected http: HttpClient) {}

  create(insumo: IInsumo): Observable<EntityResponseType> {
    return this.http.post<IInsumo>(this.resourceUrl, insumo, { observe: 'response' });
  }

  update(insumo: IInsumo): Observable<EntityResponseType> {
    return this.http.put<IInsumo>(this.resourceUrl, insumo, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IInsumo>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IInsumo[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  queryByEmpresa(req?: any, empresaId?: number): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IInsumo[]>(`${this.resourceUrl}/empresa/${empresaId}`, { params: options, observe: 'response' });
  }

  queryByEmpresaTipo(empresaId: number, tipoInsumo: TipoInsumo): Observable<EntityArrayResponseType> {
    return this.http.get<IInsumo[]>(`${this.resourceUrl}/tipo/${empresaId}/${tipoInsumo}`, { observe: 'response' });
  }

  queryByEmpresaNotInTipo(empresaId: number, req: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IInsumo[]>(`${this.resourceUrl}/tipo/${empresaId}`, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
