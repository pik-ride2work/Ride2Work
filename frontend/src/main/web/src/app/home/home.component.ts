import { Component, OnInit } from '@angular/core';
import {AuthService} from "../_services/auth.service";
import {AppRoutingModule} from "../app-routing.module";
import {Router} from "@angular/router";
import {User} from "../_models/user";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.sass']
})
export class HomeComponent implements OnInit {
  company = "Goldman Sachs";
  userName = "Reginald";
  userSurname = "Tempman";

  constructor(private router : Router, private authService: AuthService) { }

  ngOnInit() {
    let user : User;
    user = this.authService.getLogged();
    this.userName = user.firstName;
    this.userSurname = user.lastName;
  }

  logout() {
    this.authService.logout();
    this.router.navigate(["login"]);
  }
}
