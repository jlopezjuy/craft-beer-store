import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Equipamiento } from 'app/shared/model/equipamiento.model';
import { EquipamientoService } from './equipamiento.service';
import { EquipamientoComponent } from './equipamiento.component';
import { EquipamientoDetailComponent } from './equipamiento-detail.component';
import { EquipamientoUpdateComponent } from './equipamiento-update.component';
import { IEquipamiento } from 'app/shared/model/equipamiento.model';

@Injectable({ providedIn: 'root' })
export class EquipamientoResolve implements Resolve<IEquipamiento> {
  constructor(private service: EquipamientoService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEquipamiento> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((equipamiento: HttpResponse<Equipamiento>) => equipamiento.body));
    }
    return of(new Equipamiento());
  }
}

export const equipamientoRoute: Routes = [
  {
    path: '',
    component: EquipamientoComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'craftBeerStoreApp.equipamiento.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: EquipamientoDetailComponent,
    resolve: {
      equipamiento: EquipamientoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'craftBeerStoreApp.equipamiento.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: EquipamientoUpdateComponent,
    resolve: {
      equipamiento: EquipamientoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'craftBeerStoreApp.equipamiento.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: EquipamientoUpdateComponent,
    resolve: {
      equipamiento: EquipamientoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'craftBeerStoreApp.equipamiento.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
