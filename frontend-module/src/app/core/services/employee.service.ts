import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { LoadingBarService } from '@ngx-loading-bar/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { Page } from '@core/models/paging.model';
import { EmployeeModel } from '@core/models/employee.model';
import {environment} from "@env";
import {AwardsSummaryStats} from "@core/models/AwardsSummaryStats";

@Injectable({ providedIn: 'root' })
export class EmployeeService {

    baseUrl = `${environment.organizationUrl}/employees/v1`;

    private headers = new HttpHeaders().set('Content-Type', 'application/json');

    constructor(private http: HttpClient,
                private loadingBar: LoadingBarService,
                private router: Router) {
    }

    get(page: number, query: string): Observable<Page<EmployeeModel>> {

        let params = `page=${page}`;

        if(query.length) {
            params += `&query=${query}`
        }

        return this.http.get<Page<EmployeeModel>>(`${this.baseUrl}?${params}`);
    }

    getTotalAwards(): Observable<AwardsSummaryStats> {
        return this.http.get<AwardsSummaryStats>(`${this.baseUrl}/awards-summary-stats`);
    }

    getById(id: number): Observable<EmployeeModel> {
        return this.http.get<EmployeeModel>(`${this.baseUrl}/${id}`);
    }

    create(model: EmployeeModel): Observable<EmployeeModel> {
        return this.http.post<EmployeeModel>(`${this.baseUrl}`, model);
    }

    update(id: number, model: EmployeeModel): Observable<EmployeeModel> {
        return this.http.put<EmployeeModel>(`${this.baseUrl}/${id}`, model);
    }
}
