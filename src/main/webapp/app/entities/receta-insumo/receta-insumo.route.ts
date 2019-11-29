import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { RecetaInsumo } from 'app/shared/model/receta-insumo.model';
import { RecetaInsumoService } from './receta-insumo.service';
import { RecetaInsumoComponent } from './receta-insumo.component';
import { RecetaInsumoDetailComponent } from './receta-insumo-detail.component';
import { RecetaInsumoUpdateComponent } from './receta-insumo-update.component';
import { IRecetaInsumo } from 'app/shared/model/receta-insumo.model';

@Injectable({ providedIn: 'root' })
export class RecetaInsumoResolve implements Resolve<IRecetaInsumo> {
  constructor(private service: RecetaInsumoService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRecetaInsumo> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((recetaInsumo: HttpResponse<RecetaInsumo>) => recetaInsumo.body));
    }
    return of(new RecetaInsumo());
  }
}

export const recetaInsumoRoute: Routes = [
  {
    path: '',
    component: RecetaInsumoComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'craftBeerStoreApp.recetaInsumo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: RecetaInsumoDetailComponent,
    resolve: {
      recetaInsumo: RecetaInsumoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'craftBeerStoreApp.recetaInsumo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: RecetaInsumoUpdateComponent,
    resolve: {
      recetaInsumo: RecetaInsumoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'craftBeerStoreApp.recetaInsumo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: RecetaInsumoUpdateComponent,
    resolve: {
      recetaInsumo: RecetaInsumoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'craftBeerStoreApp.recetaInsumo.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
