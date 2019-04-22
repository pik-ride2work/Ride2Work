import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {AlertService, AuthService, UserService} from "../_services";
import {User} from "../_models/user";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.sass']
})
export class RegisterComponent implements OnInit {

  constructor(private router: Router,
              private authService: AuthService,
              private alertService: AlertService,
              private userService: UserService) {
  }

  username: string;
  password: string;
  firstName: string;
  lastName: string;
  email: string;

  loading = false;

  ngOnInit() {
  }

  register() {
    let user = new User(this.username, this.password, this.firstName, this.lastName, this.email);

    this.userService.register(user).subscribe(
      user => {
        this.router.navigate([""]);
      },
      error => {
        this.alertService.error(error);
      }
    )
  }

}
