import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { MedicionLote } from 'app/shared/model/medicion-lote.model';
import { MedicionLoteService } from './medicion-lote.service';
import { MedicionLoteComponent } from './medicion-lote.component';
import { MedicionLoteDetailComponent } from './medicion-lote-detail.component';
import { MedicionLoteUpdateComponent } from './medicion-lote-update.component';
import { MedicionLoteDeletePopupComponent } from './medicion-lote-delete-dialog.component';
import { IMedicionLote } from 'app/shared/model/medicion-lote.model';

@Injectable({ providedIn: 'root' })
export class MedicionLoteResolve implements Resolve<IMedicionLote> {
  constructor(private service: MedicionLoteService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IMedicionLote> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<MedicionLote>) => response.ok),
        map((medicionLote: HttpResponse<MedicionLote>) => medicionLote.body)
      );
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

export const medicionLotePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: MedicionLoteDeletePopupComponent,
    resolve: {
      medicionLote: MedicionLoteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'craftBeerStoreApp.medicionLote.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
