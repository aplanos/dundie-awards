import { Component } from '@angular/core';
import { LayoutService } from './service/app.layout.service';
import { UserModel } from '@core/models/user.model';
import { AuthService } from '@core/services/auth.service';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';

@Component({
    selector: 'app-menu',
    templateUrl: './app.menu.component.html'
})
export class AppMenuComponent {

    user: UserModel | undefined;
    model: any[] = [];

    constructor(
        private router: Router,
        private route: ActivatedRoute,
        private authService: AuthService,
        private translate: TranslateService,
        public layoutService: LayoutService) {

        this.authService.user.subscribe(x => {
            route.data.subscribe(data => {
                this.buildMenu();
            });
        });
    }

    buildMenu() {
        this.model = []

        this.router.config.forEach(r => {
            if (!r.data || r.data['hide'] || (r.data['permissions'].length > 0 &&
                !r.data['permissions'].some((permission: string) => this.authService.havePermission(permission)))) {
                return;
            }

            const group = this.translate.instant(r.data['group']);

            let parentItem = this.model.find(p => p.label === group);

            if (!parentItem) {
                parentItem = {
                    label: group,
                    items: []
                }
                this.model.push(parentItem);
            }

            parentItem.items.push({
                label: this.translate.instant(r.data['title']),
                icon: r.data['icon'],
                routerLink: [`/${r.path}`]
            })
        });
    }
}
