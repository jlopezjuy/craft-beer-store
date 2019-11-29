import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Presentacion } from 'app/shared/model/presentacion.model';
import { PresentacionService } from './presentacion.service';
import { PresentacionComponent } from './presentacion.component';
import { PresentacionDetailComponent } from './presentacion-detail.component';
import { PresentacionUpdateComponent } from './presentacion-update.component';
import { IPresentacion } from 'app/shared/model/presentacion.model';

@Injectable({ providedIn: 'root' })
export class PresentacionResolve implements Resolve<IPresentacion> {
  constructor(private service: PresentacionService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPresentacion> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((presentacion: HttpResponse<Presentacion>) => presentacion.body));
    }
    return of(new Presentacion());
  }
}

export const presentacionRoute: Routes = [
  {
    path: '',
    component: PresentacionComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'craftBeerStoreApp.presentacion.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PresentacionDetailComponent,
    resolve: {
      presentacion: PresentacionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'craftBeerStoreApp.presentacion.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PresentacionUpdateComponent,
    resolve: {
      presentacion: PresentacionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'craftBeerStoreApp.presentacion.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PresentacionUpdateComponent,
    resolve: {
      presentacion: PresentacionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'craftBeerStoreApp.presentacion.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
