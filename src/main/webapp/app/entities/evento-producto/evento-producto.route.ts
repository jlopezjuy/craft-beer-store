import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { EventoProducto } from 'app/shared/model/evento-producto.model';
import { EventoProductoService } from './evento-producto.service';
import { EventoProductoComponent } from './evento-producto.component';
import { EventoProductoDetailComponent } from './evento-producto-detail.component';
import { EventoProductoUpdateComponent } from './evento-producto-update.component';
import { EventoProductoDeletePopupComponent } from './evento-producto-delete-dialog.component';
import { IEventoProducto } from 'app/shared/model/evento-producto.model';

@Injectable({ providedIn: 'root' })
export class EventoProductoResolve implements Resolve<IEventoProducto> {
    constructor(private service: EventoProductoService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IEventoProducto> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<EventoProducto>) => response.ok),
                map((eventoProducto: HttpResponse<EventoProducto>) => eventoProducto.body)
            );
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

export const eventoProductoPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: EventoProductoDeletePopupComponent,
        resolve: {
            eventoProducto: EventoProductoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'craftBeerStoreApp.eventoProducto.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
