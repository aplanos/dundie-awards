import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from '@core/guards/auth.guard';

import { HomeComponent } from './home.component';

//const routes: Routes = [{ path: '', component: HomeComponent, canActivate: [AuthGuard] }];
const routes: Routes = [{ path: '', component: HomeComponent, canActivate: [AuthGuard]  }];

@NgModule({
   imports: [RouterModule.forChild(routes)],
   exports: [RouterModule],
})
export class HomeRoutingModule {}
