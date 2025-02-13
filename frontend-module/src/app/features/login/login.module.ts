import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import { CommonModule } from '@angular/common';

import { LoginRoutingModule } from './login-routing.module';
import {LoginComponent} from './login.component';
import {MatDialogModule} from "@angular/material/dialog";
import {ReactiveFormsModule} from "@angular/forms";
import { TranslateModule } from '@ngx-translate/core';
import { CheckboxModule } from 'primeng/checkbox';
import { ButtonModule } from 'primeng/button';
import { RippleModule } from 'primeng/ripple';
import { InputTextModule } from 'primeng/inputtext';

@NgModule({
   declarations: [LoginComponent],
    imports: [CommonModule, LoginRoutingModule, MatDialogModule, ReactiveFormsModule, TranslateModule, CheckboxModule, ButtonModule, RippleModule, InputTextModule],
   schemas: [
      CUSTOM_ELEMENTS_SCHEMA
   ]
})
export class LoginModule {}
