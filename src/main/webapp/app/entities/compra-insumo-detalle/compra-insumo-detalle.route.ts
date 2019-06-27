import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CompraInsumoDetalle } from 'app/shared/model/compra-insumo-detalle.model';
import { CompraInsumoDetalleService } from './compra-insumo-detalle.service';
import { CompraInsumoDetalleComponent } from './compra-insumo-detalle.component';
import { CompraInsumoDetalleDetailComponent } from './compra-insumo-detalle-detail.component';
import { CompraInsumoDetalleUpdateComponent } from './compra-insumo-detalle-update.component';
import { CompraInsumoDetalleDeletePopupComponent } from './compra-insumo-detalle-delete-dialog.component';
import { ICompraInsumoDetalle } from 'app/shared/model/compra-insumo-detalle.model';

@Injectable({ providedIn: 'root' })
export class CompraInsumoDetalleResolve implements Resolve<ICompraInsumoDetalle> {
  constructor(private service: CompraInsumoDetalleService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICompraInsumoDetalle> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<CompraInsumoDetalle>) => response.ok),
        map((compraInsumoDetalle: HttpResponse<CompraInsumoDetalle>) => compraInsumoDetalle.body)
      );
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

export const compraInsumoDetallePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: CompraInsumoDetalleDeletePopupComponent,
    resolve: {
      compraInsumoDetalle: CompraInsumoDetalleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'craftBeerStoreApp.compraInsumoDetalle.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
