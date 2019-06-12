import { Routes, RouterModule } from '@angular/router';
import { ModuleWithProviders } from '@angular/core';
import { AuthGuard } from './core';

export const routes: Routes = [
    { path: '', redirectTo: 'admin', pathMatch: 'full' },
    { path: 'admin', canActivate: [AuthGuard], loadChildren: () => import('app/admin/admin.module').then(m => m.AdminModule) },
    { path: 'authentication', loadChildren: () => import('app/authentication/authentication.module').then(m => m.AuthenticationModule) }
];

export const routing: ModuleWithProviders = RouterModule.forRoot(routes, { useHash: false });
