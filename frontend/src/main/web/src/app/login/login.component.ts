import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {MatDialog} from '@angular/material'

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.sass']
})
export class LoginComponent implements OnInit {
  constructor(private router: Router) {
  }

  username: string;
  password: string;

  ngOnInit() {
  }

  authorize() : boolean {
    if(!this.username || !this.password)
      return false;
    return true;
  }

  login(): void {
    if (this.authorize()) {
      this.router.navigate(["home"]);
    } else {
      alert("Invalid credentials");
    }
  }
}

