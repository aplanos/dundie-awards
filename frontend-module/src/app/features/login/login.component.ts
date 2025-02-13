import { Component } from '@angular/core';
import { AuthService } from '@core/services/auth.service';
import { Router } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { UserDetail } from '@core/models/auth.model';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { LoadingBarService } from '@ngx-loading-bar/core';
import { UserModel } from '@core/models/user.model';

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.scss']
})
export class LoginComponent {

    showPassword = false;

    entityForm = new FormGroup({
        username: new FormControl('', Validators.required),
        password: new FormControl('', Validators.required)
    });

    constructor(private authService: AuthService,
                private router: Router,
                private dialog: MatDialog,
                private loadingBar: LoadingBarService) {
    }

    toggleShow() {
        this.showPassword = !this.showPassword;
    }


    login() {

        if (this.entityForm.valid) {
            let loader = this.loadingBar.useRef();
            loader.start();

            this.authService.login(<UserDetail>this.entityForm.value).subscribe({
                next: (user: UserModel) => {
                    this.authService.storeUser(user);
                    this.router.navigateByUrl('/home').then();
                    loader.stop();
                },
                error: err => {
                    loader.stop();
                }
            });
        }
    }
}
