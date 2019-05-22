import { Route } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { CraftBeerStoreComponent } from './component';

export const CRAFT_BEER_STORE_ROUTE: Route = {
    path: 'craft-beer-store',
    component: CraftBeerStoreComponent,
    data: {
        authorities: [],
        pageTitle: 'craft-beer-store.page.title'
    },
    canActivate: [UserRouteAccessService]
};
