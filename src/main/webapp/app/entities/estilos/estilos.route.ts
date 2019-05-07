import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Estilos } from 'app/shared/model/estilos.model';
import { EstilosService } from './estilos.service';
import { EstilosComponent } from './estilos.component';
import { EstilosDetailComponent } from './estilos-detail.component';
import { EstilosUpdateComponent } from './estilos-update.component';
import { EstilosDeletePopupComponent } from './estilos-delete-dialog.component';
import { IEstilos } from 'app/shared/model/estilos.model';

@Injectable({ providedIn: 'root' })
export class EstilosResolve implements Resolve<IEstilos> {
    constructor(private service: EstilosService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IEstilos> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Estilos>) => response.ok),
                map((estilos: HttpResponse<Estilos>) => estilos.body)
            );
        }
        return of(new Estilos());
    }
}

export const estilosRoute: Routes = [
    {
        path: '',
        component: EstilosComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'craftBeerStoreApp.estilos.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: EstilosDetailComponent,
        resolve: {
            estilos: EstilosResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'craftBeerStoreApp.estilos.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: EstilosUpdateComponent,
        resolve: {
            estilos: EstilosResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'craftBeerStoreApp.estilos.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: EstilosUpdateComponent,
        resolve: {
            estilos: EstilosResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'craftBeerStoreApp.estilos.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const estilosPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: EstilosDeletePopupComponent,
        resolve: {
            estilos: EstilosResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'craftBeerStoreApp.estilos.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
