import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";
import {AuthService} from "../_services";
import {User} from "../_models/user";

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.sass']
})
export class MenuComponent implements OnInit {
  private userName: string;
  private userSurname: string;

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

  rideHistory() {
    this.router.navigate([""]);
  }

  editUser() {
    this.router.navigate(["edit-user"]);
  }
}
