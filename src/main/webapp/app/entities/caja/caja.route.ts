import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Caja } from 'app/shared/model/caja.model';
import { CajaService } from './caja.service';
import { CajaComponent } from './caja.component';
import { CajaDetailComponent } from './caja-detail.component';
import { CajaUpdateComponent } from './caja-update.component';
import { CajaDeletePopupComponent } from './caja-delete-dialog.component';
import { ICaja } from 'app/shared/model/caja.model';

@Injectable({ providedIn: 'root' })
export class CajaResolve implements Resolve<ICaja> {
    constructor(private service: CajaService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICaja> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Caja>) => response.ok),
                map((caja: HttpResponse<Caja>) => caja.body)
            );
        }
        return of(new Caja());
    }
}

export const cajaRoute: Routes = [
    {
        path: 'caja',
        component: CajaComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Movimientos de Caja Chica'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'caja/:id/view',
        component: CajaDetailComponent,
        resolve: {
            caja: CajaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'craftBeerStoreApp.caja.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'caja/new',
        component: CajaUpdateComponent,
        resolve: {
            caja: CajaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'craftBeerStoreApp.caja.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'caja/:id/edit',
        component: CajaUpdateComponent,
        resolve: {
            caja: CajaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'craftBeerStoreApp.caja.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const cajaPopupRoute: Routes = [
    {
        path: 'caja/:id/delete',
        component: CajaDeletePopupComponent,
        resolve: {
            caja: CajaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'craftBeerStoreApp.caja.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
