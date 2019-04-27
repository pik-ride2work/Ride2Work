import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {AlertService, AuthService, UserService} from "../_services";
import {User} from "../_models/user";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.sass']
})
export class RegisterComponent implements OnInit {

  constructor(private router: Router,
              private authService: AuthService,
              private alertService: AlertService,
              private userService: UserService,
              private formBuilder: FormBuilder) {
  }

  options: FormGroup;

  loading = false;

  ngOnInit() {
    this.options = this.formBuilder.group({
      username: ['', Validators.minLength(8)],
      password: ['', Validators.minLength(8)],
      firstName: [''],
      lastName: [''],
      email: ['', Validators.email]
    });
  }

  getControl(controlName) {
    if(this.options.controls[controlName])
      return this.options.controls[controlName].value || "";
    return "";
  }

  register() {
    this.loading = true;
    let user = new User(
      this.getControl('username'),
      this.getControl('password'),
      this.getControl('firstName'),
      this.getControl('lastName'),
      this.getControl('email')
    );

    this.userService.register(user).subscribe(
      user => {
        this.router.navigate([""]);
      },
      error => {
        this.loading = false;
        this.alertService.serverSideError();
      }
    )
  }

}
