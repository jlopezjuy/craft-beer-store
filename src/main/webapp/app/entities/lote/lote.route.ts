import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Lote } from 'app/shared/model/lote.model';
import { LoteService } from './lote.service';
import { LoteComponent } from './lote.component';
import { LoteDetailComponent } from './lote-detail.component';
import { LoteUpdateComponent } from './lote-update.component';
import { ILote } from 'app/shared/model/lote.model';

@Injectable({ providedIn: 'root' })
export class LoteResolve implements Resolve<ILote> {
  constructor(private service: LoteService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILote> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((lote: HttpResponse<Lote>) => lote.body));
    }
    return of(new Lote());
  }
}

export const loteRoute: Routes = [
  {
    path: '',
    component: LoteComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'craftBeerStoreApp.lote.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: LoteDetailComponent,
    resolve: {
      lote: LoteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'craftBeerStoreApp.lote.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: LoteUpdateComponent,
    resolve: {
      lote: LoteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'craftBeerStoreApp.lote.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: LoteUpdateComponent,
    resolve: {
      lote: LoteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'craftBeerStoreApp.lote.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
