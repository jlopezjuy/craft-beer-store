import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IRecetaInsumo } from 'app/shared/model/receta-insumo.model';
import { TipoInsumo } from 'app/shared/model/insumo.model';

type EntityResponseType = HttpResponse<IRecetaInsumo>;
type EntityArrayResponseType = HttpResponse<IRecetaInsumo[]>;

@Injectable({ providedIn: 'root' })
export class RecetaInsumoService {
    public resourceUrl = SERVER_API_URL + 'api/receta-insumos';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/receta-insumos';

    constructor(protected http: HttpClient) {}

    create(recetaInsumo: IRecetaInsumo): Observable<EntityResponseType> {
        return this.http.post<IRecetaInsumo>(this.resourceUrl, recetaInsumo, { observe: 'response' });
    }

    update(recetaInsumo: IRecetaInsumo): Observable<EntityResponseType> {
        return this.http.put<IRecetaInsumo>(this.resourceUrl, recetaInsumo, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IRecetaInsumo>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IRecetaInsumo[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    queryByInsumo(recetaId: number, tipoInsumo: TipoInsumo): Observable<EntityArrayResponseType> {
        return this.http.get<IRecetaInsumo[]>(`${this.resourceUrl}/receta-insumo-tipo/${recetaId}/${tipoInsumo}`, { observe: 'response' });
    }

    queryByInsumoNotIn(recetaId: number, req: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IRecetaInsumo[]>(`${this.resourceUrl}/receta-insumo-tipo/${recetaId}`, {
            params: options,
            observe: 'response'
        });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    deleteAll(insumos: any): Observable<HttpResponse<any>> {
        const options = createRequestOption(insumos);
        return this.http.delete<any>(`${this.resourceUrl}/delete`, { params: options, observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IRecetaInsumo[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
