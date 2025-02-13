import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import { CommonModule } from '@angular/common';

import {MatDialogModule} from "@angular/material/dialog";
import {ReactiveFormsModule} from "@angular/forms";
import { HomeComponent } from '@features/home/home.component';
import { SharedModule } from '@shared/shared.module';
import { HomeRoutingModule } from '@features/home/home-routing.module';
import { Button } from 'primeng/button';
import { TranslateModule } from '@ngx-translate/core';

@NgModule({
   declarations: [HomeComponent],
   imports: [CommonModule, HomeRoutingModule, MatDialogModule, ReactiveFormsModule, SharedModule, Button, TranslateModule],
   schemas: [
      CUSTOM_ELEMENTS_SCHEMA
   ]
})
export class HomeModule {}
