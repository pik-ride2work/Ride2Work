import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {AlertService, AuthService, UserService} from "../_services";
import {User} from "../_models/user";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-edit-user',
  templateUrl: './edit-user.component.html',
  styleUrls: ['./edit-user.component.sass']
})
export class EditUserComponent implements OnInit {
  constructor(private router: Router,
              private authService: AuthService,
              private alertService: AlertService,
              private userService: UserService,
              private formBuilder: FormBuilder) {
  }

  options: FormGroup;

  loading = false;

  currentUser: User;

  ngOnInit() {
    let user: User;
    user = this.authService.getLogged();
    this.currentUser = user;
    this.options = this.formBuilder.group({
      username: [this.currentUser.username, Validators.minLength(8)],
      password: [this.currentUser.password, Validators.minLength(8)],
      firstName: [this.currentUser.firstName],
      lastName: [this.currentUser.lastName],
      email: [this.currentUser.email, Validators.email]
    });
  }

  getControl(controlName) {
    if(this.options.controls[controlName])
      return this.options.controls[controlName].value || "";
    return "";
  }

  save() {
    this.loading = true;

    let user = this.currentUser;
    user.username = this.getControl("username");
    user.password = this.getControl("password");
    user.firstName = this.getControl("firstName");
    user.lastName = this.getControl("lastName");
    user.email = this.getControl("email");

    this.userService.update(user).subscribe(
      data => {
        this.authService.login(data);
        location.reload();
      },
      error => {
        this.alertService.serverSideError();
        this.loading = false;
      }
    )
  }
}
