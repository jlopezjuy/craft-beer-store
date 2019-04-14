import { Route } from '@angular/router';

import { NavbarFuseComponent } from './navbar-fuse.component';

export const navbarRoute: Route = {
    path: '',
    component: NavbarFuseComponent,
    outlet: 'navbar'
};
