import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';

import { EmployeeRoutingModule } from './employee-routing.module';
import { SharedModule } from '@shared/shared.module';
import {MatDialogModule} from "@angular/material/dialog";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {TableModule} from "primeng/table";
import {CardModule} from "primeng/card";
import {InputTextModule} from "primeng/inputtext";
import {MessagesModule} from "primeng/messages";
import {TooltipModule} from "primeng/tooltip";
import {ButtonModule} from "primeng/button";
import {BlockUIModule} from "primeng/blockui";
import { ToastModule } from 'primeng/toast';
import { ToolbarModule } from 'primeng/toolbar';
import { FileUploadModule } from 'primeng/fileupload';
import { RippleModule } from 'primeng/ripple';
import { DialogModule } from 'primeng/dialog';
import { TranslateModule } from '@ngx-translate/core';
import { InputGroupAddonModule } from 'primeng/inputgroupaddon';
import { InputGroupModule } from 'primeng/inputgroup';
import { PasswordModule } from 'primeng/password';
import { DividerModule } from 'primeng/divider';
import { AccordionModule } from 'primeng/accordion';
import { CheckboxModule } from 'primeng/checkbox';
import { DropdownModule } from 'primeng/dropdown';
import {EmployeeComponent} from "@features/employee/employee.component";
import {BadgeModule} from "primeng/badge";

@NgModule({
   declarations: [
       EmployeeComponent
   ],
    imports: [CommonModule, EmployeeRoutingModule, SharedModule, MatDialogModule,
        FormsModule, SharedModule, TableModule,
        ReactiveFormsModule, CardModule, InputTextModule, MessagesModule, TooltipModule, ButtonModule, BlockUIModule, ToastModule, ToolbarModule, FileUploadModule, RippleModule, DialogModule, TranslateModule, InputGroupAddonModule, InputGroupModule, PasswordModule, DividerModule, AccordionModule, CheckboxModule, DropdownModule, BadgeModule]
})
export class EmployeeModule {}
