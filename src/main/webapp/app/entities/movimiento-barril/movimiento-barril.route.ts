import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { MovimientoBarril } from 'app/shared/model/movimiento-barril.model';
import { MovimientoBarrilService } from './movimiento-barril.service';
import { MovimientoBarrilComponent } from './movimiento-barril.component';
import { MovimientoBarrilDetailComponent } from './movimiento-barril-detail.component';
import { MovimientoBarrilUpdateComponent } from './movimiento-barril-update.component';
import { MovimientoBarrilDeletePopupComponent } from './movimiento-barril-delete-dialog.component';
import { IMovimientoBarril } from 'app/shared/model/movimiento-barril.model';

@Injectable({ providedIn: 'root' })
export class MovimientoBarrilResolve implements Resolve<IMovimientoBarril> {
  constructor(private service: MovimientoBarrilService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IMovimientoBarril> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<MovimientoBarril>) => response.ok),
        map((movimientoBarril: HttpResponse<MovimientoBarril>) => movimientoBarril.body)
      );
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

export const movimientoBarrilPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: MovimientoBarrilDeletePopupComponent,
    resolve: {
      movimientoBarril: MovimientoBarrilResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'craftBeerStoreApp.movimientoBarril.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
