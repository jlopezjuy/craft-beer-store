import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Barril } from 'app/shared/model/barril.model';
import { BarrilService } from './barril.service';
import { BarrilComponent } from './barril.component';
import { BarrilDetailComponent } from './barril-detail.component';
import { BarrilUpdateComponent } from './barril-update.component';
import { BarrilDeletePopupComponent } from './barril-delete-dialog.component';
import { IBarril } from 'app/shared/model/barril.model';

@Injectable({ providedIn: 'root' })
export class BarrilResolve implements Resolve<IBarril> {
  constructor(private service: BarrilService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IBarril> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Barril>) => response.ok),
        map((barril: HttpResponse<Barril>) => barril.body)
      );
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

export const barrilPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: BarrilDeletePopupComponent,
    resolve: {
      barril: BarrilResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'craftBeerStoreApp.barril.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
