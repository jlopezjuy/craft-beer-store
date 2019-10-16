import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { MovimientoTanque } from 'app/shared/model/movimiento-tanque.model';
import { MovimientoTanqueService } from './movimiento-tanque.service';
import { MovimientoTanqueComponent } from './movimiento-tanque.component';
import { MovimientoTanqueDetailComponent } from './movimiento-tanque-detail.component';
import { MovimientoTanqueUpdateComponent } from './movimiento-tanque-update.component';
import { MovimientoTanqueDeletePopupComponent } from './movimiento-tanque-delete-dialog.component';
import { IMovimientoTanque } from 'app/shared/model/movimiento-tanque.model';

@Injectable({ providedIn: 'root' })
export class MovimientoTanqueResolve implements Resolve<IMovimientoTanque> {
  constructor(private service: MovimientoTanqueService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IMovimientoTanque> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<MovimientoTanque>) => response.ok),
        map((movimientoTanque: HttpResponse<MovimientoTanque>) => movimientoTanque.body)
      );
    }
    return of(new MovimientoTanque());
  }
}

export const movimientoTanqueRoute: Routes = [
  {
    path: '',
    component: MovimientoTanqueComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'craftBeerStoreApp.movimientoTanque.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: MovimientoTanqueDetailComponent,
    resolve: {
      movimientoTanque: MovimientoTanqueResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'craftBeerStoreApp.movimientoTanque.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: MovimientoTanqueUpdateComponent,
    resolve: {
      movimientoTanque: MovimientoTanqueResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'craftBeerStoreApp.movimientoTanque.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: MovimientoTanqueUpdateComponent,
    resolve: {
      movimientoTanque: MovimientoTanqueResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'craftBeerStoreApp.movimientoTanque.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const movimientoTanquePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: MovimientoTanqueDeletePopupComponent,
    resolve: {
      movimientoTanque: MovimientoTanqueResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'craftBeerStoreApp.movimientoTanque.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
