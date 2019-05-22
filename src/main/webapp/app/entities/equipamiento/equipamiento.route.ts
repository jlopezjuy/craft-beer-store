import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Equipamiento } from 'app/shared/model/equipamiento.model';
import { EquipamientoService } from './equipamiento.service';
import { EquipamientoComponent } from './equipamiento.component';
import { EquipamientoDetailComponent } from './equipamiento-detail.component';
import { EquipamientoUpdateComponent } from './equipamiento-update.component';
import { EquipamientoDeletePopupComponent } from './equipamiento-delete-dialog.component';
import { IEquipamiento } from 'app/shared/model/equipamiento.model';

@Injectable({ providedIn: 'root' })
export class EquipamientoResolve implements Resolve<IEquipamiento> {
    constructor(private service: EquipamientoService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IEquipamiento> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Equipamiento>) => response.ok),
                map((equipamiento: HttpResponse<Equipamiento>) => equipamiento.body)
            );
        }
        return of(new Equipamiento());
    }
}

export const equipamientoRoute: Routes = [
    {
        path: '',
        component: EquipamientoComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'craftBeerStoreApp.equipamiento.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: EquipamientoDetailComponent,
        resolve: {
            equipamiento: EquipamientoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'craftBeerStoreApp.equipamiento.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: EquipamientoUpdateComponent,
        resolve: {
            equipamiento: EquipamientoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'craftBeerStoreApp.equipamiento.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: EquipamientoUpdateComponent,
        resolve: {
            equipamiento: EquipamientoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'craftBeerStoreApp.equipamiento.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const equipamientoPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: EquipamientoDeletePopupComponent,
        resolve: {
            equipamiento: EquipamientoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'craftBeerStoreApp.equipamiento.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
