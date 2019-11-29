import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { MovimientoTanque } from 'app/shared/model/movimiento-tanque.model';
import { MovimientoTanqueService } from './movimiento-tanque.service';
import { MovimientoTanqueComponent } from './movimiento-tanque.component';
import { MovimientoTanqueDetailComponent } from './movimiento-tanque-detail.component';
import { MovimientoTanqueUpdateComponent } from './movimiento-tanque-update.component';
import { IMovimientoTanque } from 'app/shared/model/movimiento-tanque.model';

@Injectable({ providedIn: 'root' })
export class MovimientoTanqueResolve implements Resolve<IMovimientoTanque> {
  constructor(private service: MovimientoTanqueService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMovimientoTanque> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((movimientoTanque: HttpResponse<MovimientoTanque>) => movimientoTanque.body));
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
