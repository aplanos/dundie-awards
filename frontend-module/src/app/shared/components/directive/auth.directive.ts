import {Directive, Input, OnInit, TemplateRef, ViewContainerRef} from '@angular/core';
import {AuthService} from "@core/services/auth.service";

@Directive({selector: '[hasPermission]'})
export class HasPermissionDirective implements OnInit {
  constructor(
    private templateRef: TemplateRef<any>,
    private authService: AuthService,
    private viewContainer: ViewContainerRef
  ) {
  }

  permission: string = '';

  @Input() set hasPermission(permission: string) {
    this.permission = permission;
  }

  ngOnInit() {
      const permissions: string[] = this.permission.split(',');
      const hasFun: any = !!permissions.find(p => this.authService.havePermission(p));

      if (!hasFun) {
          this.viewContainer.clear();
      } else {
          this.viewContainer.createEmbeddedView(this.templateRef);
      }
  }
}
