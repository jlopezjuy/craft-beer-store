import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { DetalleMovimiento } from 'app/shared/model/detalle-movimiento.model';
import { DetalleMovimientoService } from './detalle-movimiento.service';
import { DetalleMovimientoComponent } from './detalle-movimiento.component';
import { DetalleMovimientoDetailComponent } from './detalle-movimiento-detail.component';
import { DetalleMovimientoUpdateComponent } from './detalle-movimiento-update.component';
import { IDetalleMovimiento } from 'app/shared/model/detalle-movimiento.model';

@Injectable({ providedIn: 'root' })
export class DetalleMovimientoResolve implements Resolve<IDetalleMovimiento> {
  constructor(private service: DetalleMovimientoService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDetalleMovimiento> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((detalleMovimiento: HttpResponse<DetalleMovimiento>) => detalleMovimiento.body));
    }
    return of(new DetalleMovimiento());
  }
}

export const detalleMovimientoRoute: Routes = [
  {
    path: '',
    component: DetalleMovimientoComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'craftBeerStoreApp.detalleMovimiento.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DetalleMovimientoDetailComponent,
    resolve: {
      detalleMovimiento: DetalleMovimientoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'craftBeerStoreApp.detalleMovimiento.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DetalleMovimientoUpdateComponent,
    resolve: {
      detalleMovimiento: DetalleMovimientoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'craftBeerStoreApp.detalleMovimiento.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DetalleMovimientoUpdateComponent,
    resolve: {
      detalleMovimiento: DetalleMovimientoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'craftBeerStoreApp.detalleMovimiento.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
