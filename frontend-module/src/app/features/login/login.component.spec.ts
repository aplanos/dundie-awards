import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { SharedModule } from '../../shared/shared.module';

import { LoginComponent } from './login.component';
import {HttpClientModule} from "@angular/common/http";
import {RouterModule} from "@angular/router";
import {APP_BASE_HREF} from "@angular/common";

describe('LoginComponent', () => {
   let component: LoginComponent;
   let fixture: ComponentFixture<LoginComponent>;

   beforeEach(waitForAsync(() => {
      TestBed.configureTestingModule({
         declarations: [LoginComponent],
         imports: [HttpClientModule, RouterModule.forRoot([]), SharedModule],
         schemas: [CUSTOM_ELEMENTS_SCHEMA],
         providers: [{provide: APP_BASE_HREF, useValue : '/' }]
      }).compileComponents();
   }));

   beforeEach(() => {
      fixture = TestBed.createComponent(LoginComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
   });

   it('should create', () => {
      expect(component).toBeTruthy();
   });
});
