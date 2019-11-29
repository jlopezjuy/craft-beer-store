import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Barril } from 'app/shared/model/barril.model';
import { BarrilService } from './barril.service';
import { BarrilComponent } from './barril.component';
import { BarrilDetailComponent } from './barril-detail.component';
import { BarrilUpdateComponent } from './barril-update.component';
import { IBarril } from 'app/shared/model/barril.model';

@Injectable({ providedIn: 'root' })
export class BarrilResolve implements Resolve<IBarril> {
  constructor(private service: BarrilService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBarril> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((barril: HttpResponse<Barril>) => barril.body));
    }
    return of(new Barril());
  }
}

export const barrilRoute: Routes = [
  {
    path: '',
    component: BarrilComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'craftBeerStoreApp.barril.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: BarrilDetailComponent,
    resolve: {
      barril: BarrilResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'craftBeerStoreApp.barril.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: BarrilUpdateComponent,
    resolve: {
      barril: BarrilResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'craftBeerStoreApp.barril.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: BarrilUpdateComponent,
    resolve: {
      barril: BarrilResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'craftBeerStoreApp.barril.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
