import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { EventoProducto } from 'app/shared/model/evento-producto.model';
import { EventoProductoService } from './evento-producto.service';
import { EventoProductoComponent } from './evento-producto.component';
import { EventoProductoDetailComponent } from './evento-producto-detail.component';
import { EventoProductoUpdateComponent } from './evento-producto-update.component';
import { IEventoProducto } from 'app/shared/model/evento-producto.model';

@Injectable({ providedIn: 'root' })
export class EventoProductoResolve implements Resolve<IEventoProducto> {
  constructor(private service: EventoProductoService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEventoProducto> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((eventoProducto: HttpResponse<EventoProducto>) => eventoProducto.body));
    }
    return of(new EventoProducto());
  }
}

export const eventoProductoRoute: Routes = [
  {
    path: '',
    component: EventoProductoComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'craftBeerStoreApp.eventoProducto.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: EventoProductoDetailComponent,
    resolve: {
      eventoProducto: EventoProductoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'craftBeerStoreApp.eventoProducto.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: EventoProductoUpdateComponent,
    resolve: {
      eventoProducto: EventoProductoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'craftBeerStoreApp.eventoProducto.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: EventoProductoUpdateComponent,
    resolve: {
      eventoProducto: EventoProductoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'craftBeerStoreApp.eventoProducto.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
