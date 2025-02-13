import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { MenuItem } from 'primeng/api';
import { LayoutService } from "./service/app.layout.service";
import { TranslateService } from '@ngx-translate/core';
import { AuthService } from '@core/services/auth.service';

@Component({
    selector: 'app-topbar',
    templateUrl: './app.topbar.component.html'
})
export class AppTopBarComponent implements OnInit {

    items!: MenuItem[];

    @ViewChild('menubutton') menuButton!: ElementRef;

    @ViewChild('topbarmenubutton') topbarMenuButton!: ElementRef;

    @ViewChild('topbarmenu') menu!: ElementRef;

    constructor(public layoutService: LayoutService,
                public translateService: TranslateService,
                public authService: AuthService,
                ) { }

    ngOnInit() {
        this.items = [
            {
                label: this.translateService.instant('MENU.OPTIONS'),
                items: [
                    {
                        label: this.translateService.instant('MENU.LOGOUT'),
                        icon: 'pi pi-sign-out',
                        command: () => {
                            this.authService.logout();
                        }
                    }
                ]
            }
        ];
    }
}
