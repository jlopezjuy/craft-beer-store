import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPuntoDeVenta } from 'app/shared/model/punto-de-venta.model';

type EntityResponseType = HttpResponse<IPuntoDeVenta>;
type EntityArrayResponseType = HttpResponse<IPuntoDeVenta[]>;

@Injectable({ providedIn: 'root' })
export class PuntoDeVentaService {
    public resourceUrl = SERVER_API_URL + 'api/punto-de-ventas';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/punto-de-ventas';

    constructor(protected http: HttpClient) {}

    create(puntoDeVenta: IPuntoDeVenta): Observable<EntityResponseType> {
        return this.http.post<IPuntoDeVenta>(this.resourceUrl, puntoDeVenta, { observe: 'response' });
    }

    update(puntoDeVenta: IPuntoDeVenta): Observable<EntityResponseType> {
        return this.http.put<IPuntoDeVenta>(this.resourceUrl, puntoDeVenta, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IPuntoDeVenta>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IPuntoDeVenta[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    queryByCliente(req?: any, clienteId?: number): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IPuntoDeVenta[]>(`${this.resourceUrl}/cliente/${clienteId}`, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IPuntoDeVenta[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
