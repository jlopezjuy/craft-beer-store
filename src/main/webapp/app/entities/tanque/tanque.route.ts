import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Tanque } from 'app/shared/model/tanque.model';
import { TanqueService } from './tanque.service';
import { TanqueComponent } from './tanque.component';
import { TanqueDetailComponent } from './tanque-detail.component';
import { TanqueUpdateComponent } from './tanque-update.component';
import { TanqueDeletePopupComponent } from './tanque-delete-dialog.component';
import { ITanque } from 'app/shared/model/tanque.model';

@Injectable({ providedIn: 'root' })
export class TanqueResolve implements Resolve<ITanque> {
  constructor(private service: TanqueService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ITanque> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Tanque>) => response.ok),
        map((tanque: HttpResponse<Tanque>) => tanque.body)
      );
    }
    return of(new Tanque());
  }
}

export const tanqueRoute: Routes = [
  {
    path: '',
    component: TanqueComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'craftBeerStoreApp.tanque.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TanqueDetailComponent,
    resolve: {
      tanque: TanqueResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'craftBeerStoreApp.tanque.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TanqueUpdateComponent,
    resolve: {
      tanque: TanqueResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'craftBeerStoreApp.tanque.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TanqueUpdateComponent,
    resolve: {
      tanque: TanqueResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'craftBeerStoreApp.tanque.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const tanquePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: TanqueDeletePopupComponent,
    resolve: {
      tanque: TanqueResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'craftBeerStoreApp.tanque.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
