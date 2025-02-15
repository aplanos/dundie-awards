import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { OrganizationComponent } from './organization.component';
import {AuthGuard} from "@core/guards/auth.guard";

const routes: Routes = [
    {
        path: '',
        component: OrganizationComponent,
        data: { permissions: [] },
        canActivate: [AuthGuard]
    }
];

@NgModule({
   imports: [RouterModule.forChild(routes)],
   exports: [RouterModule],
})
export class OrganizationRoutingModule {}
