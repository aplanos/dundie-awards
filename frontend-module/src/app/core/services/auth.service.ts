import { AuthToken, UserDetail } from '@core/models/auth.model';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';

import { CONSTANTS } from './constants';
import { Injectable } from '@angular/core';
import { UserModel } from '@core/models/user.model';
import { BehaviorSubject, Observable } from 'rxjs';
import { RoleEnum } from '@core/enums/role.enum';
import { Router } from '@angular/router';
import { LoadingBarService } from '@ngx-loading-bar/core';
import { environment } from '@env';


@Injectable({ providedIn: 'root' })
export class AuthService {

    baseUrl = `${environment.authUrl}/auth/v1`;

    public user: Observable<UserModel>;
    private userSubject: BehaviorSubject<UserModel>;

    private headers = new HttpHeaders().set('Content-Type', 'application/json');

    constructor(private _http: HttpClient,
                private loadingBar: LoadingBarService,
                private router: Router) {

        this.userSubject = new BehaviorSubject<UserModel>(JSON.parse(<string>localStorage.getItem('user')));
        this.user = this.userSubject.asObservable();

    }

    public get userValue(): UserModel {
        const model = new UserModel();
        model.clone(this.userSubject.value);
        return model;
    }

    login(userDetails: UserDetail) {

        const headers = this.headers
            .append('Identity', userDetails.username)

        return this._http.post<UserModel>(`${this.baseUrl}/login`, JSON.stringify(userDetails), {
            headers
        });
    }

    logout() {

        localStorage.clear();
        this.router.navigateByUrl('/login');
    }

    isLoggedIn() {
        return !!this.getToken(); // add your strong logic
    }

    storeUser(user: UserModel) {
        localStorage.setItem('user', JSON.stringify(user));
        localStorage.setItem('access_token', user.token as string);

        this.userSubject.next(user);
    }

    storeToken(token: AuthToken) {
        localStorage.setItem('access_token', token.access_token);
        localStorage.setItem(CONSTANTS.REFRESH_TOKEN, token.refresh_token);
    }

    getToken() {
        return localStorage.getItem('access_token');
    }

    refreshToken() {
        const body = new HttpParams().set(CONSTANTS.REFRESH_TOKEN, this.getRefreshToken()!);
        return this._http.post<AuthToken>(`${this.baseUrl}/refresh`, body.toString(), {
            headers: this.headers
        });
    }

    getRefreshToken() {
        return localStorage.getItem(CONSTANTS.REFRESH_TOKEN);
    }

    public isAdmin(): boolean {
        return this.haveRole('ADMIN');
    }

    public haveRole(roleName: string): boolean {

        const currentUser = this.userSubject.value;

        if (!currentUser || !currentUser.roles) {
            return false;
        }

        return currentUser.roles.some(role => role === roleName);
    }

    public haveRoles(roles: RoleEnum []): boolean {

        const currentUser = this.userSubject.value;

        if (!currentUser || !currentUser.roles) {
            return false;
        }

        return currentUser.roles.some(role => roles.some(r => r === role));
    }

    public userHaveRole(user: UserModel, roleName: string): boolean {

        if (!user || !user.roles) {
            return false;
        }

        return user.roles.some(role => role === roleName);
    }

    public havePermission(permission: string): boolean {
        return !!this.userSubject.value.permissions?.find(p => p === permission);
    }
}
