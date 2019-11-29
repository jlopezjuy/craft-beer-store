import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { MovimientoBarril } from 'app/shared/model/movimiento-barril.model';
import { MovimientoBarrilService } from './movimiento-barril.service';
import { MovimientoBarrilComponent } from './movimiento-barril.component';
import { MovimientoBarrilDetailComponent } from './movimiento-barril-detail.component';
import { MovimientoBarrilUpdateComponent } from './movimiento-barril-update.component';
import { IMovimientoBarril } from 'app/shared/model/movimiento-barril.model';

@Injectable({ providedIn: 'root' })
export class MovimientoBarrilResolve implements Resolve<IMovimientoBarril> {
  constructor(private service: MovimientoBarrilService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMovimientoBarril> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((movimientoBarril: HttpResponse<MovimientoBarril>) => movimientoBarril.body));
    }
    return of(new MovimientoBarril());
  }
}

export const movimientoBarrilRoute: Routes = [
  {
    path: '',
    component: MovimientoBarrilComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'craftBeerStoreApp.movimientoBarril.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: MovimientoBarrilDetailComponent,
    resolve: {
      movimientoBarril: MovimientoBarrilResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'craftBeerStoreApp.movimientoBarril.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: MovimientoBarrilUpdateComponent,
    resolve: {
      movimientoBarril: MovimientoBarrilResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'craftBeerStoreApp.movimientoBarril.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: MovimientoBarrilUpdateComponent,
    resolve: {
      movimientoBarril: MovimientoBarrilResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'craftBeerStoreApp.movimientoBarril.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
