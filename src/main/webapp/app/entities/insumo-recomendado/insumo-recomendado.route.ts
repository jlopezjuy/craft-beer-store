import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { InsumoRecomendado } from 'app/shared/model/insumo-recomendado.model';
import { InsumoRecomendadoService } from './insumo-recomendado.service';
import { InsumoRecomendadoComponent } from './insumo-recomendado.component';
import { InsumoRecomendadoDetailComponent } from './insumo-recomendado-detail.component';
import { InsumoRecomendadoUpdateComponent } from './insumo-recomendado-update.component';
import { InsumoRecomendadoDeletePopupComponent } from './insumo-recomendado-delete-dialog.component';
import { IInsumoRecomendado } from 'app/shared/model/insumo-recomendado.model';

@Injectable({ providedIn: 'root' })
export class InsumoRecomendadoResolve implements Resolve<IInsumoRecomendado> {
    constructor(private service: InsumoRecomendadoService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IInsumoRecomendado> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<InsumoRecomendado>) => response.ok),
                map((insumoRecomendado: HttpResponse<InsumoRecomendado>) => insumoRecomendado.body)
            );
        }
        return of(new InsumoRecomendado());
    }
}

export const insumoRecomendadoRoute: Routes = [
    {
        path: '',
        component: InsumoRecomendadoComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'craftBeerStoreApp.insumoRecomendado.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: InsumoRecomendadoDetailComponent,
        resolve: {
            insumoRecomendado: InsumoRecomendadoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'craftBeerStoreApp.insumoRecomendado.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: InsumoRecomendadoUpdateComponent,
        resolve: {
            insumoRecomendado: InsumoRecomendadoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'craftBeerStoreApp.insumoRecomendado.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: InsumoRecomendadoUpdateComponent,
        resolve: {
            insumoRecomendado: InsumoRecomendadoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'craftBeerStoreApp.insumoRecomendado.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const insumoRecomendadoPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: InsumoRecomendadoDeletePopupComponent,
        resolve: {
            insumoRecomendado: InsumoRecomendadoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'craftBeerStoreApp.insumoRecomendado.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];