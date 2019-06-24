import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICaja } from 'app/shared/model/caja.model';

type EntityResponseType = HttpResponse<ICaja>;
type EntityArrayResponseType = HttpResponse<ICaja[]>;

@Injectable({ providedIn: 'root' })
export class CajaService {
    public resourceUrl = SERVER_API_URL + 'api/cajas';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/cajas';

    constructor(protected http: HttpClient) {}

    create(caja: ICaja): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(caja);
        return this.http
            .post<ICaja>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(caja: ICaja): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(caja);
        return this.http
            .put<ICaja>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ICaja>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ICaja[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    queryByEmpresa(req?: any, empresaId?: number): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ICaja[]>(`${this.resourceUrl}/empresa/${empresaId}`, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ICaja[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    findIngresoEgreso(empresaId: number): Observable<EntityResponseType> {
        return this.http
            .get<ICaja>(`${this.resourceUrl}/graph-ingreso-egreso/${empresaId}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    findIngresoWeek(empresaId: number): Observable<EntityArrayResponseType> {
        return this.http
            .get<ICaja[]>(`${this.resourceUrl}/semana/${empresaId}`, { observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(caja: ICaja): ICaja {
        const copy: ICaja = Object.assign({}, caja, {
            fecha: caja.fecha != null && caja.fecha.isValid() ? caja.fecha.format(DATE_FORMAT) : null
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
            res.body.forEach((caja: ICaja) => {
                caja.fecha = caja.fecha != null ? moment(caja.fecha) : null;
            });
        }
        return res;
    }
}
