import { HttpHandlerFn, HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { catchError, switchMap, filter, take, finalize, throwError, BehaviorSubject, Observable, tap } from 'rxjs';
import { HttpRequest, HttpHandler, HttpEvent, HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { AuthService } from '@core/services/auth.service';
import { BroadcasterService } from '@core/services/broadcaster.service';
import { CONSTANTS } from '@core/services/constants';
import { environment } from '@env';

export const httpInterceptor: HttpInterceptorFn = (req, next) => {

    if (req.url.includes('i18n')) {
        req = req.clone({
            setHeaders: {
                'Content-Type': 'application/json; charset=utf-8',
                Accept: 'application/json'
            }
        });
    } else {
        const authService = inject(AuthService);
        const broadcaster = inject(BroadcasterService);
        const baseUrl = environment.apiUrl;

        let isAlreadyRefreshing = false;
        const tokenSubject = new BehaviorSubject<any>(null);

        broadcaster.broadcast(CONSTANTS.SHOW_LOADER, true);

        // Clone and modify the request, if not loading local assets
        let newReq = req;
        if (!req.url.startsWith('../assets')) {
            newReq = req.clone({
                url: `${baseUrl}${req.url}`,
                headers: req.headers.set(CONSTANTS.AUTH_HEADER, `Bearer ${authService.getToken()}`)
            });
        }

        // Return the modified request and handle the response
        return next(newReq).pipe(
            tap((event) => {
                if (event instanceof HttpResponse) {
                    handleSuccess(event.body);
                }
            }),
            catchError((err) => handleError(newReq, next, err)),
            finalize(() => {
                broadcaster.broadcast(CONSTANTS.SHOW_LOADER, false);
            })
        );

        function handleSuccess(body: any) {
            // Handle success actions here
        }

        function handleError(request: HttpRequest<any>, next: HttpHandlerFn, error: any): Observable<HttpEvent<any>> {
            console.log("error:", request.url);
            if (error instanceof HttpErrorResponse && error.status === 401) {
                // Handle 401 unauthorized error by trying to refresh the token
                return handle401(request, next);
            } else {
                broadcaster.broadcast(CONSTANTS.ERROR, {
                    error: 'Algo deu errado. Por favor, entre em contato com o Administrador do Sistema.',
                    timeout: 5000
                });
                return throwError(error);
            }
        }

        function addToken(request: HttpRequest<any>, newToken: any) {
            return request.clone({
                headers: request.headers.set(CONSTANTS.AUTH_HEADER, `Bearer ${newToken.access_token}`)
            });
        }

        function handle401(request: HttpRequest<any>, next: HttpHandlerFn): Observable<HttpEvent<any>> {
            if (!isAlreadyRefreshing) {
                isAlreadyRefreshing = true;
                tokenSubject.next(null);

                return authService.refreshToken().pipe(
                    switchMap((newToken) => {
                        if (newToken) {
                            authService.storeToken(newToken);
                            tokenSubject.next(newToken);
                            return next(addToken(request, newToken));
                        }
                        authService.logout();
                        return throwError(() => new Error('No refresh token found'));
                    }),
                    catchError((error) => {
                        authService.logout();
                        return throwError(() => new Error(error.message));
                    }),
                    finalize(() => {
                        isAlreadyRefreshing = false;
                    })
                );
            } else {
                // Wait for the new token to be available if the token refresh process is already ongoing
                return tokenSubject.pipe(
                    filter((token) => token != null),
                    take(1),
                    switchMap((token) => next(addToken(request, token)))
                );
            }
        }
    }

    return next(req);
};
