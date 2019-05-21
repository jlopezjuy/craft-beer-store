import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { PuntoDeVenta } from 'app/shared/model/punto-de-venta.model';
import { PuntoDeVentaService } from './punto-de-venta.service';
import { PuntoDeVentaComponent } from './punto-de-venta.component';
import { PuntoDeVentaDetailComponent } from './punto-de-venta-detail.component';
import { PuntoDeVentaUpdateComponent } from './punto-de-venta-update.component';
import { PuntoDeVentaDeletePopupComponent } from './punto-de-venta-delete-dialog.component';
import { IPuntoDeVenta } from 'app/shared/model/punto-de-venta.model';

@Injectable({ providedIn: 'root' })
export class PuntoDeVentaResolve implements Resolve<IPuntoDeVenta> {
    constructor(private service: PuntoDeVentaService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPuntoDeVenta> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<PuntoDeVenta>) => response.ok),
                map((puntoDeVenta: HttpResponse<PuntoDeVenta>) => puntoDeVenta.body)
            );
        }
        return of(new PuntoDeVenta());
    }
}

export const puntoDeVentaRoute: Routes = [
    {
        path: '',
        component: PuntoDeVentaComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'craftBeerStoreApp.puntoDeVenta.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: PuntoDeVentaDetailComponent,
        resolve: {
            puntoDeVenta: PuntoDeVentaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'craftBeerStoreApp.puntoDeVenta.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: PuntoDeVentaUpdateComponent,
        resolve: {
            puntoDeVenta: PuntoDeVentaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'craftBeerStoreApp.puntoDeVenta.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: PuntoDeVentaUpdateComponent,
        resolve: {
            puntoDeVenta: PuntoDeVentaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'craftBeerStoreApp.puntoDeVenta.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const puntoDeVentaPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: PuntoDeVentaDeletePopupComponent,
        resolve: {
            puntoDeVenta: PuntoDeVentaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'craftBeerStoreApp.puntoDeVenta.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
