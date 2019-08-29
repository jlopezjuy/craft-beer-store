import { Routes, RouterModule } from '@angular/router';
import { ModuleWithProviders } from '@angular/core';
import { AuthGuard } from './core';

export const routes: Routes = [
  { path: '', redirectTo: 'admin/dashboard/index', pathMatch: 'full' },
  { path: 'admin', canActivate: [AuthGuard], loadChildren: './admin/admin.module#AdminModule', canActivateChild: [AuthGuard] },
  { path: 'authentication', loadChildren: './authentication/authentication.module#AuthenticationModule' }
];

export const routing: ModuleWithProviders = RouterModule.forRoot(routes, { useHash: true });
