import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Tanque } from 'app/shared/model/tanque.model';
import { TanqueService } from './tanque.service';
import { TanqueComponent } from './tanque.component';
import { TanqueDetailComponent } from './tanque-detail.component';
import { TanqueUpdateComponent } from './tanque-update.component';
import { ITanque } from 'app/shared/model/tanque.model';

@Injectable({ providedIn: 'root' })
export class TanqueResolve implements Resolve<ITanque> {
  constructor(private service: TanqueService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITanque> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((tanque: HttpResponse<Tanque>) => tanque.body));
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
