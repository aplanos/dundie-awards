import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { LoadingBarService } from '@ngx-loading-bar/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { Page } from '@core/models/paging.model';
import { EmployeeModel } from '@core/models/employee.model';
import {ActivityModel} from "@core/models/activity.model";

@Injectable({ providedIn: 'root' })
export class ActivityService {

    private baseUrl = '/activities/v1';

    private headers = new HttpHeaders().set('Content-Type', 'application/json');

    constructor(private http: HttpClient,
                private loadingBar: LoadingBarService,
                private router: Router) {
    }

    get(page: number, query: string): Observable<Page<ActivityModel>> {

        let params = `page=${page}`;

        if(query.length) {
            params += `&query=${query}`
        }

        return this.http.get<Page<ActivityModel>>(`${this.baseUrl}?${params}`);
    }
}
