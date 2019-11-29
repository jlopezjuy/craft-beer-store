import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Receta } from 'app/shared/model/receta.model';
import { RecetaService } from './receta.service';
import { RecetaComponent } from './receta.component';
import { RecetaDetailComponent } from './receta-detail.component';
import { RecetaUpdateComponent } from './receta-update.component';
import { IReceta } from 'app/shared/model/receta.model';

@Injectable({ providedIn: 'root' })
export class RecetaResolve implements Resolve<IReceta> {
  constructor(private service: RecetaService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IReceta> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((receta: HttpResponse<Receta>) => receta.body));
    }
    return of(new Receta());
  }
}

export const recetaRoute: Routes = [
  {
    path: '',
    component: RecetaComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'craftBeerStoreApp.receta.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: RecetaDetailComponent,
    resolve: {
      receta: RecetaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'craftBeerStoreApp.receta.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: RecetaUpdateComponent,
    resolve: {
      receta: RecetaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'craftBeerStoreApp.receta.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: RecetaUpdateComponent,
    resolve: {
      receta: RecetaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'craftBeerStoreApp.receta.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
