import { Routes } from '@angular/router';
import { AppLayoutComponent } from '@shared/layout/app.layout.component';

export const routes: Routes = [
    {
        path: 'home',
        component: AppLayoutComponent,
        loadChildren: () => import('./features/home/home.module').then((m) => m.HomeModule),
        data: { group: 'MENU.DASHBOARD', title: 'MENU.ANALYTICS', icon: 'pi pi-fw pi-home', permissions: [] }
    },
    {
        path: 'employee',
        component: AppLayoutComponent,
        loadChildren: () => import('./features/employee/employee.module').then((m) => m.EmployeeModule),
        data: { group: 'MENU.CRM', title: 'MENU.CUSTOMERS', icon: 'pi pi-fw pi-user', permissions: [] }
    },
    {
        path: 'login',
        loadChildren: () => import('./features/login/login.module').then((m) => m.LoginModule)
    },
    {
        path: '**',
        redirectTo: 'login' // or 404 module
    }
];
