import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from '@core/guards/auth.guard';

import { EmployeeComponent } from './employee.component';

const routes: Routes = [
    {
        path: '',
        component: EmployeeComponent,
        data: { permissions: [] },
        canActivate: [AuthGuard]
    }
];

@NgModule({
   imports: [RouterModule.forChild(routes)],
   exports: [RouterModule],
})
export class EmployeeRoutingModule {}
