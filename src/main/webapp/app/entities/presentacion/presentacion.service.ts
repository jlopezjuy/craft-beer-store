import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPresentacion } from 'app/shared/model/presentacion.model';

type EntityResponseType = HttpResponse<IPresentacion>;
type EntityArrayResponseType = HttpResponse<IPresentacion[]>;

@Injectable({ providedIn: 'root' })
export class PresentacionService {
    public resourceUrl = SERVER_API_URL + 'api/presentacions';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/presentacions';

    constructor(protected http: HttpClient) {}

    create(presentacion: IPresentacion): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(presentacion);
        return this.http
            .post<IPresentacion>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(presentacion: IPresentacion): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(presentacion);
        return this.http
            .put<IPresentacion>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IPresentacion>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IPresentacion[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    queryByProducto(req?: any, productoId?: number): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IPresentacion[]>(`${this.resourceUrl}/producto/${productoId}`, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IPresentacion[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(presentacion: IPresentacion): IPresentacion {
        const copy: IPresentacion = Object.assign({}, presentacion, {
            fecha: presentacion.fecha != null && presentacion.fecha.isValid() ? presentacion.fecha.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.fecha = res.body.fecha != null ? moment(res.body.fecha) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((presentacion: IPresentacion) => {
                presentacion.fecha = presentacion.fecha != null ? moment(presentacion.fecha) : null;
            });
        }
        return res;
    }
}
