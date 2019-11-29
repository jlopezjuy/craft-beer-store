import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { CompraInsumo } from 'app/shared/model/compra-insumo.model';
import { CompraInsumoService } from './compra-insumo.service';
import { CompraInsumoComponent } from './compra-insumo.component';
import { CompraInsumoDetailComponent } from './compra-insumo-detail.component';
import { CompraInsumoUpdateComponent } from './compra-insumo-update.component';
import { ICompraInsumo } from 'app/shared/model/compra-insumo.model';

@Injectable({ providedIn: 'root' })
export class CompraInsumoResolve implements Resolve<ICompraInsumo> {
  constructor(private service: CompraInsumoService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICompraInsumo> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((compraInsumo: HttpResponse<CompraInsumo>) => compraInsumo.body));
    }
    return of(new CompraInsumo());
  }
}

export const compraInsumoRoute: Routes = [
  {
    path: '',
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
    path: ':id/view',
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
    path: 'new',
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
    path: ':id/edit',
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
