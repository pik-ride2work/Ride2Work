import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.sass']
})
export class HomeComponent implements OnInit {
  company = "Goldman Sachs";
  userName = "Reginald";
  userSurname = "Tempman";

  constructor(private router: Router) { }

  ngOnInit() {
  }

  logout() {
    this.router.navigate([""]);
  }
}
