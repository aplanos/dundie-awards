import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from '@core/guards/auth.guard';

import { ActivityComponent } from './activity.component';

const routes: Routes = [
    {
        path: '',
        component: ActivityComponent,
        data: { permissions: [] },
        canActivate: [AuthGuard]
    }
];

@NgModule({
   imports: [RouterModule.forChild(routes)],
   exports: [RouterModule],
})
export class ActivityRoutingModule {}
