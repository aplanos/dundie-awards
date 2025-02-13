import { RoleEnum } from "@core/enums/role.enum";

export class MenuItem {

    name = "unknown";
    isOpen = false;
    children: SubMenuItem[] = [];

    constructor(name: string, isOpen: boolean) {
        this.name = name;
        this.isOpen = isOpen;
    }

    addChild(subMenuItem: SubMenuItem) {
        this.children.push(subMenuItem);
    }
}

export class SubMenuItem {
    path: string;
    data: MenuData | undefined;

    constructor(path: string, data: MenuData) {
        this.path = path;
        this.data = data;
    }
}

export class MenuData {
    group: string;
    title: string;
    icon: string;
    roles: RoleEnum[] = [];

    constructor(group: string, title: string, icon: string, roles: RoleEnum[]) {
        this.group = group;
        this.title = title;
        this.icon = icon;
        this.roles = roles;
    }
}