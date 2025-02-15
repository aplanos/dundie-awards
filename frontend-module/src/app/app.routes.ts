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
        data: { group: 'MENU.FEATURES', title: 'MENU.EMPLOYEES', icon: 'pi pi-fw pi-user', permissions: [] }
    },
    {
        path: 'activity',
        component: AppLayoutComponent,
        loadChildren: () => import('./features/activity/activity.module').then((m) => m.ActivityModule),
        data: { group: 'MENU.FEATURES', title: 'MENU.ACTIVITIES', icon: 'pi pi-fw pi-cog', permissions: [] }
    },
    {
        path: 'organization',
        component: AppLayoutComponent,
        loadChildren: () => import('./features/organization/organization.module').then((m) => m.OrganizationModule),
        data: { group: 'MENU.FEATURES', title: 'MENU.ORGANIZATIONS', icon: 'pi pi-fw pi-cog', permissions: [] }
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
