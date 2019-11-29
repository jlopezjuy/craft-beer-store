import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { CompraInsumoDetalle } from 'app/shared/model/compra-insumo-detalle.model';
import { CompraInsumoDetalleService } from './compra-insumo-detalle.service';
import { CompraInsumoDetalleComponent } from './compra-insumo-detalle.component';
import { CompraInsumoDetalleDetailComponent } from './compra-insumo-detalle-detail.component';
import { CompraInsumoDetalleUpdateComponent } from './compra-insumo-detalle-update.component';
import { ICompraInsumoDetalle } from 'app/shared/model/compra-insumo-detalle.model';

@Injectable({ providedIn: 'root' })
export class CompraInsumoDetalleResolve implements Resolve<ICompraInsumoDetalle> {
  constructor(private service: CompraInsumoDetalleService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICompraInsumoDetalle> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((compraInsumoDetalle: HttpResponse<CompraInsumoDetalle>) => compraInsumoDetalle.body));
    }
    return of(new CompraInsumoDetalle());
  }
}

export const compraInsumoDetalleRoute: Routes = [
  {
    path: '',
    component: CompraInsumoDetalleComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'craftBeerStoreApp.compraInsumoDetalle.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CompraInsumoDetalleDetailComponent,
    resolve: {
      compraInsumoDetalle: CompraInsumoDetalleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'craftBeerStoreApp.compraInsumoDetalle.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CompraInsumoDetalleUpdateComponent,
    resolve: {
      compraInsumoDetalle: CompraInsumoDetalleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'craftBeerStoreApp.compraInsumoDetalle.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CompraInsumoDetalleUpdateComponent,
    resolve: {
      compraInsumoDetalle: CompraInsumoDetalleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'craftBeerStoreApp.compraInsumoDetalle.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
