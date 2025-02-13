import { Component } from '@angular/core';
import { MessageService, PrimeNGConfig } from 'primeng/api';
import { TranslateService } from '@ngx-translate/core';
import { RouterOutlet } from '@angular/router';
import { PrimeNGTranslationPt } from '@core/primeng/primeng-pt';
import { LoadingBarRouterModule } from '@ngx-loading-bar/router';
import { ToastModule } from 'primeng/toast';
import { Subject, takeUntil } from 'rxjs';
import { CONSTANTS } from '@core/services/constants';
import { BroadcasterService } from '@core/services/broadcaster.service';

@Component({
    selector: 'app-root',
    standalone: true,
    imports: [RouterOutlet, LoadingBarRouterModule, ToastModule],
    templateUrl: './app.component.html',
    styleUrl: './app.component.scss'
})
export class AppComponent {


    $destroy: Subject<void> = new Subject();

    constructor(private broadcaster: BroadcasterService,
                private translate: TranslateService,
                private messageService: MessageService,
                private primengConfig: PrimeNGConfig) {
        this.primengConfig.setTranslation(new PrimeNGTranslationPt());
        this.translate.setDefaultLang('pt');
        this.translate.use('pt');

        /**
         * do this in other page, for e.g I'm doing here only
         * use this service with takeUntil from rxJS and local Subject to prevent memory leaks like shown
         */
        this.broadcaster
            .listen(CONSTANTS.ERROR)
            .pipe(takeUntil(this.$destroy))
            .subscribe({
                next: (data: any) => {
                    this.messageService.add({
                        severity: 'error',
                        summary: 'Error',
                        detail: data.error
                    });
                }
            });
    }

    ngOnDestroy() {
        this.$destroy.next();
        this.$destroy.complete();
    }

}
