import { ApplicationConfig, importProvidersFrom, provideZoneChangeDetection } from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import { TranslateLoader, TranslateModule, TranslateService } from '@ngx-translate/core';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClient, provideHttpClient, withInterceptors } from '@angular/common/http';
import { httpInterceptor } from '@core/interceptors/http-req-res.interceptor';
import { MessageService } from 'primeng/api';
import { CONSTANTS } from '@core/services/constants';
import { environment } from '@env';

import localePt from '@angular/common/locales/pt';
import localeEn from '@angular/common/locales/en';
import localeSp from '@angular/common/locales/es';

import { registerLocaleData } from '@angular/common';
import { enableProdMode, LOCALE_ID } from '@angular/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { AppLayoutModule } from '@shared/layout/app.layout.module';

const defaultLanguage: string = CONSTANTS.DEFAULT_LANGUAGE;

registerLocaleData(localePt);
registerLocaleData(localeEn);
registerLocaleData(localeSp);

if (environment.production) {
    enableProdMode();
}

export function createTranslateLoader(http: HttpClient) {
    return new TranslateHttpLoader(http);
}

export function translateServiceFactory(translateService: TranslateService) {
    if (!translateService?.currentLang) {
        return defaultLanguage;
    }

    return translateService.currentLang;
}

export function getLocaleProvider() {
    return {
        provide: LOCALE_ID,
        deps: [TranslateService],
        useFactory: translateServiceFactory
    };
}

export const appConfig: ApplicationConfig = {
    providers: [
        TranslateService,
        importProvidersFrom(BrowserModule),
        importProvidersFrom(BrowserAnimationsModule),
        importProvidersFrom(AppLayoutModule),
        provideHttpClient(
            withInterceptors([httpInterceptor])
        ),
        getLocaleProvider(),
        importProvidersFrom(TranslateModule.forRoot({
            loader: {
                provide: TranslateLoader,
                useFactory: createTranslateLoader,
                deps: [HttpClient]
            },
            defaultLanguage
        })),
        MessageService,
        provideZoneChangeDetection({ eventCoalescing: true }),
        provideRouter(routes)
    ]
};
