import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot } from '@angular/router';

import { Injectable } from '@angular/core';
import { AuthService } from '@core/services/auth.service';

@Injectable({ providedIn: 'root' })
export class AuthGuard implements CanActivate {
   constructor(private authService: AuthService, private router: Router) {}

   canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {

      if(route.component?.name == 'LandingPageComponent') {
         return true
      }

      if (!this.authService.isLoggedIn()) {
         this.router.navigate(['login']).then();
         return false;
      }

      if (route.data['permissions'] && route.data['permissions'].length > 0) {

         const hasPermission = route.data['permissions']
             .some((p: string) => this.authService.havePermission(p));

         if (!hasPermission) {
            this.router.navigate(['home']).then();
            return false;
         }
      }

      return true;
   }
}
