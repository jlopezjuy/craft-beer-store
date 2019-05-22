import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IEquipamiento } from 'app/shared/model/equipamiento.model';

type EntityResponseType = HttpResponse<IEquipamiento>;
type EntityArrayResponseType = HttpResponse<IEquipamiento[]>;

@Injectable({ providedIn: 'root' })
export class EquipamientoService {
    public resourceUrl = SERVER_API_URL + 'api/equipamientos';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/equipamientos';

    constructor(protected http: HttpClient) {}

    create(equipamiento: IEquipamiento): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(equipamiento);
        return this.http
            .post<IEquipamiento>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(equipamiento: IEquipamiento): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(equipamiento);
        return this.http
            .put<IEquipamiento>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IEquipamiento>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IEquipamiento[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    queryByEmpresa(req?: any, empresaId?: number): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IEquipamiento[]>(`${this.resourceUrl}/empresa/${empresaId}`, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IEquipamiento[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(equipamiento: IEquipamiento): IEquipamiento {
        const copy: IEquipamiento = Object.assign({}, equipamiento, {
            fechaCompra:
                equipamiento.fechaCompra != null && equipamiento.fechaCompra.isValid() ? equipamiento.fechaCompra.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.fechaCompra = res.body.fechaCompra != null ? moment(res.body.fechaCompra) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((equipamiento: IEquipamiento) => {
                equipamiento.fechaCompra = equipamiento.fechaCompra != null ? moment(equipamiento.fechaCompra) : null;
            });
        }
        return res;
    }
}
