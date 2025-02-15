import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CurrencyPipe } from '@angular/common';
import { Page } from '@core/models/paging.model';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import {OrganizationModel} from "@core/models/organization.model";
import {OrganizationService} from "@core/services/organization.service";

@Component({
    selector: 'app-organization',
    templateUrl: './organization.component.html',
    styleUrls: ['./organization.component.scss'],
    providers: [CurrencyPipe]
})
export class OrganizationComponent {

    query = '';
    page = new Page<OrganizationModel>();
    loading = true;

    entityForm: FormGroup;

    constructor(
        private fb: FormBuilder,
        private organizationService: OrganizationService,
        private router: Router) {
        this.entityForm = this.fb.group({
            name: ['', Validators.required]
        });
    }

    setPage(pageInfo?: any){
        this.loading = true;

        const offset = pageInfo?.rows > 0 ? (pageInfo.first / pageInfo.rows) : 0;

        this.organizationService.get(offset, this.query).subscribe({
            next: (page: Page<OrganizationModel>) => {
                this.page = page;
                this.loading = false;
            },
            error: () => {
                this.loading = false;
            }
        });
    }

    giveAwards(organizationId: number) {
        this.loading = true;


        this.organizationService.giveAwards(organizationId).subscribe({
            next: () => {
                this.loading = false;
            },
            error: () => {
                this.loading = false;
            }
        });
    }
}
