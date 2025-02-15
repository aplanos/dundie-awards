import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { LoadingBarService } from '@ngx-loading-bar/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { Page } from '@core/models/paging.model';
import { EmployeeModel } from '@core/models/employee.model';
import {environment} from "@env";
import {OrganizationModel} from "@core/models/organization.model";

@Injectable({ providedIn: 'root' })
export class OrganizationService {

    baseUrl = `${environment.organizationUrl}/organizations/v1`;

    private headers = new HttpHeaders().set('Content-Type', 'application/json');

    constructor(private http: HttpClient,
                private loadingBar: LoadingBarService,
                private router: Router) {
    }

    get(page: number, query: string): Observable<Page<OrganizationModel>> {

        let params = `page=${page}`;

        if(query.length) {
            params += `&query=${query}`
        }

        return this.http.get<Page<OrganizationModel>>(`${this.baseUrl}?${params}`);
    }

    getById(id: number): Observable<OrganizationModel> {
        return this.http.get<OrganizationModel>(`${this.baseUrl}/${id}`);
    }

    create(model: OrganizationModel): Observable<OrganizationModel> {
        return this.http.post<OrganizationModel>(`${this.baseUrl}`, model);
    }

    update(id: number, model: OrganizationModel): Observable<OrganizationModel> {
        return this.http.put<OrganizationModel>(`${this.baseUrl}/${id}`, model);
    }
}
