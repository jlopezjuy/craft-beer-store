import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { MedicionLote } from 'app/shared/model/medicion-lote.model';
import { MedicionLoteService } from './medicion-lote.service';
import { MedicionLoteComponent } from './medicion-lote.component';
import { MedicionLoteDetailComponent } from './medicion-lote-detail.component';
import { MedicionLoteUpdateComponent } from './medicion-lote-update.component';
import { IMedicionLote } from 'app/shared/model/medicion-lote.model';

@Injectable({ providedIn: 'root' })
export class MedicionLoteResolve implements Resolve<IMedicionLote> {
  constructor(private service: MedicionLoteService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMedicionLote> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((medicionLote: HttpResponse<MedicionLote>) => medicionLote.body));
    }
    return of(new MedicionLote());
  }
}

export const medicionLoteRoute: Routes = [
  {
    path: '',
    component: MedicionLoteComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'craftBeerStoreApp.medicionLote.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: MedicionLoteDetailComponent,
    resolve: {
      medicionLote: MedicionLoteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'craftBeerStoreApp.medicionLote.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: MedicionLoteUpdateComponent,
    resolve: {
      medicionLote: MedicionLoteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'craftBeerStoreApp.medicionLote.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: MedicionLoteUpdateComponent,
    resolve: {
      medicionLote: MedicionLoteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'craftBeerStoreApp.medicionLote.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
