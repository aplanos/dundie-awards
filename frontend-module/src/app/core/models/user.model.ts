
export class UserModel {
    id: number | undefined;
    username: string | undefined;
    password: string | undefined;
    firstname: string | undefined;
    lastname: string | undefined;
    active: boolean | undefined;
    roles: string [] = [];
    permissions: string []  = [];
    token?: string | undefined;

    clone(user: UserModel) {

        if (!user) {
            return;
        }

        this.id = user.id;
        this.username = user.username;
        this.firstname = user.firstname;
        this.lastname = user.lastname;
        this.active = user.active;
        this.token = user.token;
        this.roles = user.roles;
    }

    public fullName(): string {
        return `${this.firstname} ${this.lastname}`;
    }

    public abbreviation(): string {

        let fAbbrev = this.firstname?.substr(0,1).toUpperCase();
        let lAbbrev = this.lastname?.substr(0,1).toUpperCase()

        return `${fAbbrev}${lAbbrev}`;
    }
}