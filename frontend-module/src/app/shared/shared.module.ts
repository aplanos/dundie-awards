import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import {FormsModule} from "@angular/forms";
import { HasPermissionDirective } from '@shared/components/directive/auth.directive';

@NgModule({
    declarations: [
        HasPermissionDirective
    ],
    imports: [CommonModule, FormsModule],
    exports: [
        HasPermissionDirective
    ],
})
export class SharedModule {}
