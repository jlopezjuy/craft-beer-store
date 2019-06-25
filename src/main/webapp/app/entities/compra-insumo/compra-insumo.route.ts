import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CompraInsumo } from 'app/shared/model/compra-insumo.model';
import { CompraInsumoService } from './compra-insumo.service';
import { CompraInsumoComponent } from './compra-insumo.component';
import { CompraInsumoDetailComponent } from './compra-insumo-detail.component';
import { CompraInsumoUpdateComponent } from './compra-insumo-update.component';
import { CompraInsumoDeletePopupComponent } from './compra-insumo-delete-dialog.component';
import { ICompraInsumo } from 'app/shared/model/compra-insumo.model';

@Injectable({ providedIn: 'root' })
export class CompraInsumoResolve implements Resolve<ICompraInsumo> {
  constructor(private service: CompraInsumoService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICompraInsumo> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<CompraInsumo>) => response.ok),
        map((compraInsumo: HttpResponse<CompraInsumo>) => compraInsumo.body)
      );
    }
    return of(new CompraInsumo());
  }
}

export const compraInsumoRoute: Routes = [
  {
    path: 'compra-insumo',
    component: CompraInsumoComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'craftBeerStoreApp.compraInsumo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'compra-insumo/:id/view',
    component: CompraInsumoDetailComponent,
    resolve: {
      compraInsumo: CompraInsumoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'craftBeerStoreApp.compraInsumo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'compra-insumo/new',
    component: CompraInsumoUpdateComponent,
    resolve: {
      compraInsumo: CompraInsumoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'craftBeerStoreApp.compraInsumo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'compra-insumo/:id/edit',
    component: CompraInsumoUpdateComponent,
    resolve: {
      compraInsumo: CompraInsumoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'craftBeerStoreApp.compraInsumo.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const compraInsumoPopupRoute: Routes = [
  {
    path: 'compra-insumo/:id/delete',
    component: CompraInsumoDeletePopupComponent,
    resolve: {
      compraInsumo: CompraInsumoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'craftBeerStoreApp.compraInsumo.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
