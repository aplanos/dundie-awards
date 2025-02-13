import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CurrencyPipe } from '@angular/common';
import { Page } from '@core/models/paging.model';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { EmployeeModel } from '@core/models/employee.model';
import { ActivityService } from '@core/services/activity.service';
import {ActivityModel} from "@core/models/activity.model";

@Component({
    selector: 'app-activity',
    templateUrl: './activity.component.html',
    styleUrls: ['./activity.component.scss'],
    providers: [CurrencyPipe]
})
export class ActivityComponent {

    query = '';
    page = new Page<ActivityModel>();
    loading = true;

    entityForm: FormGroup;

    constructor(
        private fb: FormBuilder,
        private activityService: ActivityService,
        private router: Router) {
        this.entityForm = this.fb.group({
            name: ['', Validators.required]
        });
    }

    setPage(pageInfo?: any){
        this.loading = true;

        const offset = pageInfo?.rows > 0 ? (pageInfo.first / pageInfo.rows) : 0;

        this.activityService.get(offset, this.query).subscribe({
            next: (page: Page<ActivityModel>) => {
                this.page = page;
                this.loading = false;
            },
            error: () => {
                this.loading = false;
            }
        });
    }
}
