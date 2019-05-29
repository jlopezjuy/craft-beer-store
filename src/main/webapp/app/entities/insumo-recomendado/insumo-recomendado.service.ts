import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IInsumoRecomendado } from 'app/shared/model/insumo-recomendado.model';

type EntityResponseType = HttpResponse<IInsumoRecomendado>;
type EntityArrayResponseType = HttpResponse<IInsumoRecomendado[]>;

@Injectable({ providedIn: 'root' })
export class InsumoRecomendadoService {
    public resourceUrl = SERVER_API_URL + 'api/insumo-recomendados';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/insumo-recomendados';

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

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IInsumoRecomendado[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
