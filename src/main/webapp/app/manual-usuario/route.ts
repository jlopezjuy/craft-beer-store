import { Route } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { ManualUsuarioComponent } from './component';

export const MANUAL_USUARIO_ROUTE: Route = {
    path: 'manual-usuario',
    component: ManualUsuarioComponent,
    data: {
        authorities: [],
        pageTitle: 'manual-usuario.page.title'
    },
    canActivate: [UserRouteAccessService]
};
