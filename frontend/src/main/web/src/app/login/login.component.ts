import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {MatDialog} from '@angular/material'
import {AuthService} from "../_services/auth.service";
import {first} from "rxjs/internal/operators/first";
import {AlertService, UserService} from "../_services";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.sass']
})
export class LoginComponent implements OnInit {
  constructor(private router: Router,
              private authService: AuthService,
              private alertService: AlertService,
              private userService: UserService) {
  }

  username: string;
  password: string;
  loading = false;

  ngOnInit() {
  }

  register(): void {
    this.router.navigate(["/register"]);
  }

  login(): void {
    this.loading = true;
    this.userService.get(this.username).subscribe(
      user => {
        if(!user || user.password != this.password){
          this.alertService.error("Invalid credentials");
          this.loading = false;
          return;
        }
        this.authService.login(user);
        this.router.navigate([""]);
      },
      error => {
        this.loading = false;
        this.alertService.error(error);
      }
    )
  }
}

