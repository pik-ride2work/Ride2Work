export class User {
  username: string;
  password: string;
  firstName: string;
  lastName: string;
  email: string;

  constructor(username, password, firstName, lastName, email) {
    this.username = username;
    this.password = password;
    this.firstName  = firstName;
    this.lastName = lastName;
    this.email = email;
  }
}
