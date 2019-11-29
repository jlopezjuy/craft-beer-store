import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICompraInsumoDetalle } from 'app/shared/model/compra-insumo-detalle.model';

type EntityResponseType = HttpResponse<ICompraInsumoDetalle>;
type EntityArrayResponseType = HttpResponse<ICompraInsumoDetalle[]>;

@Injectable({ providedIn: 'root' })
export class CompraInsumoDetalleService {
  public resourceUrl = SERVER_API_URL + 'api/compra-insumo-detalles';

  constructor(protected http: HttpClient) {}

  create(compraInsumoDetalle: ICompraInsumoDetalle): Observable<EntityResponseType> {
    return this.http.post<ICompraInsumoDetalle>(this.resourceUrl, compraInsumoDetalle, { observe: 'response' });
  }

  update(compraInsumoDetalle: ICompraInsumoDetalle): Observable<EntityResponseType> {
    return this.http.put<ICompraInsumoDetalle>(this.resourceUrl, compraInsumoDetalle, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICompraInsumoDetalle>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICompraInsumoDetalle[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
