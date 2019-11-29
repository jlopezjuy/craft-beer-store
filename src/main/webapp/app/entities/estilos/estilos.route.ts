import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Estilos } from 'app/shared/model/estilos.model';
import { EstilosService } from './estilos.service';
import { EstilosComponent } from './estilos.component';
import { EstilosDetailComponent } from './estilos-detail.component';
import { EstilosUpdateComponent } from './estilos-update.component';
import { IEstilos } from 'app/shared/model/estilos.model';

@Injectable({ providedIn: 'root' })
export class EstilosResolve implements Resolve<IEstilos> {
  constructor(private service: EstilosService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEstilos> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((estilos: HttpResponse<Estilos>) => estilos.body));
    }
    return of(new Estilos());
  }
}

export const estilosRoute: Routes = [
  {
    path: '',
    component: EstilosComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'craftBeerStoreApp.estilos.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: EstilosDetailComponent,
    resolve: {
      estilos: EstilosResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'craftBeerStoreApp.estilos.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: EstilosUpdateComponent,
    resolve: {
      estilos: EstilosResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'craftBeerStoreApp.estilos.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: EstilosUpdateComponent,
    resolve: {
      estilos: EstilosResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'craftBeerStoreApp.estilos.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
