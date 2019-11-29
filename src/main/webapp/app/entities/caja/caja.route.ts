import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Caja } from 'app/shared/model/caja.model';
import { CajaService } from './caja.service';
import { CajaComponent } from './caja.component';
import { CajaDetailComponent } from './caja-detail.component';
import { CajaUpdateComponent } from './caja-update.component';
import { ICaja } from 'app/shared/model/caja.model';

@Injectable({ providedIn: 'root' })
export class CajaResolve implements Resolve<ICaja> {
  constructor(private service: CajaService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICaja> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((caja: HttpResponse<Caja>) => caja.body));
    }
    return of(new Caja());
  }
}

export const cajaRoute: Routes = [
  {
    path: '',
    component: CajaComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'craftBeerStoreApp.caja.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CajaDetailComponent,
    resolve: {
      caja: CajaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'craftBeerStoreApp.caja.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CajaUpdateComponent,
    resolve: {
      caja: CajaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'craftBeerStoreApp.caja.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CajaUpdateComponent,
    resolve: {
      caja: CajaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'craftBeerStoreApp.caja.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
