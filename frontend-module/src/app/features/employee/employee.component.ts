import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CurrencyPipe } from '@angular/common';
import { Page } from '@core/models/paging.model';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { EmployeeModel } from '@core/models/employee.model';
import { EmployeeService } from '@core/services/employee.service';

@Component({
    selector: 'app-customer',
    templateUrl: './employee.component.html',
    styleUrls: ['./employee.component.scss'],
    providers: [CurrencyPipe]
})
export class EmployeeComponent {

    submitted: boolean = false;
    showDetailDialog: boolean = false;

    query = '';
    page = new Page<EmployeeModel>();
    loading = true;

    entityForm: FormGroup;

    constructor(
        private fb: FormBuilder,
        private employeeService: EmployeeService,
        private router: Router) {
        this.entityForm = this.fb.group({
            name: ['', Validators.required]
        });
    }

    setPage(pageInfo?: any){
        this.loading = true;

        const offset = pageInfo?.rows > 0 ? (pageInfo.first / pageInfo.rows) : 0;

        this.employeeService.get(offset, this.query).subscribe({
            next: (page: Page<EmployeeModel>) => {
                this.page = page;
                this.loading = false;
            },
            error: () => {
                this.loading = false;
            }
        });
    }
}
