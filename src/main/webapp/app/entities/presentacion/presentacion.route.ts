import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Presentacion } from 'app/shared/model/presentacion.model';
import { PresentacionService } from './presentacion.service';
import { PresentacionComponent } from './presentacion.component';
import { PresentacionDetailComponent } from './presentacion-detail.component';
import { PresentacionUpdateComponent } from './presentacion-update.component';
import { PresentacionDeletePopupComponent } from './presentacion-delete-dialog.component';
import { IPresentacion } from 'app/shared/model/presentacion.model';

@Injectable({ providedIn: 'root' })
export class PresentacionResolve implements Resolve<IPresentacion> {
  constructor(private service: PresentacionService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPresentacion> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Presentacion>) => response.ok),
        map((presentacion: HttpResponse<Presentacion>) => presentacion.body)
      );
    }
    return of(new Presentacion());
  }
}

export const presentacionRoute: Routes = [
  {
    path: 'presentacion',
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
    path: 'presentacion/:id/view',
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
    path: 'presentacion/new',
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
    path: 'presentacion/:id/edit',
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

export const presentacionPopupRoute: Routes = [
  {
    path: 'presentacion/:id/delete',
    component: PresentacionDeletePopupComponent,
    resolve: {
      presentacion: PresentacionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'craftBeerStoreApp.presentacion.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
