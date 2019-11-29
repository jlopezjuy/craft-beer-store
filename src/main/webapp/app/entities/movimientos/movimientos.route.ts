import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Movimientos } from 'app/shared/model/movimientos.model';
import { MovimientosService } from './movimientos.service';
import { MovimientosComponent } from './movimientos.component';
import { MovimientosDetailComponent } from './movimientos-detail.component';
import { MovimientosUpdateComponent } from './movimientos-update.component';
import { IMovimientos } from 'app/shared/model/movimientos.model';

@Injectable({ providedIn: 'root' })
export class MovimientosResolve implements Resolve<IMovimientos> {
  constructor(private service: MovimientosService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMovimientos> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((movimientos: HttpResponse<Movimientos>) => movimientos.body));
    }
    return of(new Movimientos());
  }
}

export const movimientosRoute: Routes = [
  {
    path: '',
    component: MovimientosComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'craftBeerStoreApp.movimientos.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: MovimientosDetailComponent,
    resolve: {
      movimientos: MovimientosResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'craftBeerStoreApp.movimientos.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: MovimientosUpdateComponent,
    resolve: {
      movimientos: MovimientosResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'craftBeerStoreApp.movimientos.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: MovimientosUpdateComponent,
    resolve: {
      movimientos: MovimientosResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'craftBeerStoreApp.movimientos.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
