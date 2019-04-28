import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {MatDialog, MatSnackBar} from '@angular/material'
import {AuthService} from "../_services/auth.service";
import {first} from "rxjs/internal/operators/first";
import {AlertService, UserService} from "../_services";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.sass']
})
export class LoginComponent implements OnInit {
  constructor(
    private router: Router,
    private authService: AuthService,
    private alertService: AlertService,
    private userService: UserService,
    private formBuilder: FormBuilder,
    private snackBar: MatSnackBar
  ) {
  }

  options: FormGroup;
  loading = false;

  ngOnInit() {
    this.options = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required],
    });
  }

  getControl(controlName) {
    if (this.options.controls[controlName])
      return this.options.controls[controlName].value || "";
    return "";
  }

  register(): void {
    this.router.navigate(["/register"]);
  }

  login(): void {
    if(this.options.invalid)
      return;

    this.loading = true;
    let username = this.getControl('username');
    let password = this.getControl('password');

    this.userService.get(username).subscribe(
      user => {
        if (!user || user.password != password) {
          this.snackBar.open("Ivnalid credentials", "close");
          this.loading = false;
          return;
        }
        this.authService.login(user);
        this.router.navigate([""]);
      },
      error => {
        this.alertService.serverSideError();
        this.loading = false;
      },
    )
  }
}

