import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { PuntoDeVenta } from 'app/shared/model/punto-de-venta.model';
import { PuntoDeVentaService } from './punto-de-venta.service';
import { PuntoDeVentaComponent } from './punto-de-venta.component';
import { PuntoDeVentaDetailComponent } from './punto-de-venta-detail.component';
import { PuntoDeVentaUpdateComponent } from './punto-de-venta-update.component';
import { IPuntoDeVenta } from 'app/shared/model/punto-de-venta.model';

@Injectable({ providedIn: 'root' })
export class PuntoDeVentaResolve implements Resolve<IPuntoDeVenta> {
  constructor(private service: PuntoDeVentaService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPuntoDeVenta> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((puntoDeVenta: HttpResponse<PuntoDeVenta>) => puntoDeVenta.body));
    }
    return of(new PuntoDeVenta());
  }
}

export const puntoDeVentaRoute: Routes = [
  {
    path: '',
    component: PuntoDeVentaComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'craftBeerStoreApp.puntoDeVenta.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PuntoDeVentaDetailComponent,
    resolve: {
      puntoDeVenta: PuntoDeVentaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'craftBeerStoreApp.puntoDeVenta.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PuntoDeVentaUpdateComponent,
    resolve: {
      puntoDeVenta: PuntoDeVentaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'craftBeerStoreApp.puntoDeVenta.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PuntoDeVentaUpdateComponent,
    resolve: {
      puntoDeVenta: PuntoDeVentaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'craftBeerStoreApp.puntoDeVenta.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
