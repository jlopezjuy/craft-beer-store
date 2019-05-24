import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

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
        return this.http.post<IReceta>(this.resourceUrl, receta, { observe: 'response' });
    }

    update(receta: IReceta): Observable<EntityResponseType> {
        return this.http.put<IReceta>(this.resourceUrl, receta, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IReceta>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IReceta[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    queryByProducto(req?: any, productoId?: number): Observable<EntityArrayResponseType> {
        console.log(productoId);
        const options = createRequestOption(req);
        return this.http.get<IReceta[]>(`${this.resourceUrl}/producto/${productoId}`, { params: options, observe: 'response' });
    }

    findAllByProducto(req?: any, productoId?: number): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IReceta[]>(`${this.resourceUrl}/producto/list/${productoId}`, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IReceta[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
