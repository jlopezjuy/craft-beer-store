import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { EtapaLote } from 'app/shared/model/etapa-lote.model';
import { EtapaLoteService } from './etapa-lote.service';
import { EtapaLoteComponent } from './etapa-lote.component';
import { EtapaLoteDetailComponent } from './etapa-lote-detail.component';
import { EtapaLoteUpdateComponent } from './etapa-lote-update.component';
import { EtapaLoteDeletePopupComponent } from './etapa-lote-delete-dialog.component';
import { IEtapaLote } from 'app/shared/model/etapa-lote.model';

@Injectable({ providedIn: 'root' })
export class EtapaLoteResolve implements Resolve<IEtapaLote> {
  constructor(private service: EtapaLoteService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IEtapaLote> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<EtapaLote>) => response.ok),
        map((etapaLote: HttpResponse<EtapaLote>) => etapaLote.body)
      );
    }
    return of(new EtapaLote());
  }
}

export const etapaLoteRoute: Routes = [
  {
    path: '',
    component: EtapaLoteComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'craftBeerStoreApp.etapaLote.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: EtapaLoteDetailComponent,
    resolve: {
      etapaLote: EtapaLoteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'craftBeerStoreApp.etapaLote.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: EtapaLoteUpdateComponent,
    resolve: {
      etapaLote: EtapaLoteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'craftBeerStoreApp.etapaLote.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: EtapaLoteUpdateComponent,
    resolve: {
      etapaLote: EtapaLoteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'craftBeerStoreApp.etapaLote.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const etapaLotePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: EtapaLoteDeletePopupComponent,
    resolve: {
      etapaLote: EtapaLoteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'craftBeerStoreApp.etapaLote.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
