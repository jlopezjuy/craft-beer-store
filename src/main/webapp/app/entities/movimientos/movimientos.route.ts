import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Movimientos } from 'app/shared/model/movimientos.model';
import { MovimientosService } from './movimientos.service';
import { MovimientosComponent } from './movimientos.component';
import { MovimientosDetailComponent } from './movimientos-detail.component';
import { MovimientosUpdateComponent } from './movimientos-update.component';
import { MovimientosDeletePopupComponent } from './movimientos-delete-dialog.component';
import { IMovimientos } from 'app/shared/model/movimientos.model';

@Injectable({ providedIn: 'root' })
export class MovimientosResolve implements Resolve<IMovimientos> {
    constructor(private service: MovimientosService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IMovimientos> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Movimientos>) => response.ok),
                map((movimientos: HttpResponse<Movimientos>) => movimientos.body)
            );
        }
        return of(new Movimientos());
    }
}

export const movimientosRoute: Routes = [
    {
        path: 'movimientos',
        component: MovimientosComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'craftBeerStoreApp.movimientos.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'movimientos/:id/view',
        component: MovimientosDetailComponent,
        resolve: {
            movimientos: MovimientosResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'craftBeerStoreApp.movimientos.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'movimientos/new',
        component: MovimientosUpdateComponent,
        resolve: {
            movimientos: MovimientosResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'craftBeerStoreApp.movimientos.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'movimientos/:id/edit',
        component: MovimientosUpdateComponent,
        resolve: {
            movimientos: MovimientosResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'craftBeerStoreApp.movimientos.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const movimientosPopupRoute: Routes = [
    {
        path: 'movimientos/:id/delete',
        component: MovimientosDeletePopupComponent,
        resolve: {
            movimientos: MovimientosResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'craftBeerStoreApp.movimientos.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
