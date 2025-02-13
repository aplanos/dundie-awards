import { Component } from '@angular/core';
import { AuthService } from '@core/services/auth.service';
import { Router } from '@angular/router';
import { UserModel } from '@core/models/user.model';
import { CurrencyPipe } from '@angular/common';

@Component({
    selector: 'app-home',
    templateUrl: './home.component.html',
    styleUrls: ['./home.component.scss'],
    providers: [CurrencyPipe]
})
export class HomeComponent {

    currentUser?: UserModel;

    constructor(private authService: AuthService,
                private router: Router) {

        this.authService.user.subscribe(value => {
            this.currentUser = value;
        });
    }
}
