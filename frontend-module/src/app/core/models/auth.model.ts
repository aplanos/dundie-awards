export class AuthToken {
   access_token!: string;
   refresh_token!: string;
}

export class UserDetail {
   username!: string;
   password!: string;

   constructor(username: string, password: string) {
      this.username = username;
      this.password = password;
   }

}
