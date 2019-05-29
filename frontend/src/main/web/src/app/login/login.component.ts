import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {MatSnackBar} from '@angular/material'
import {AuthService} from "../_services/auth.service";
import {AlertService, MembershipService, TeamService, UserService} from "../_services";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {User} from "../_models/user";

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
    private snackBar: MatSnackBar,
    private membershipService: MembershipService,
    private teamService: TeamService
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
    if (this.options.invalid)
      return;

    this.loading = true;
    let username = this.getControl('username');
    let password = this.getControl('password');
    let user: User = new User(username, password, undefined, undefined, undefined);

    this.userService.login(user).subscribe(
      user => {
        this.authService.setUser(user);
        this.loadTeam(user.id);
      },
      error => {
        this.snackBar.open("Invalid credentials", "close");
        this.loading = false;
      },
    )
  }

  loadTeam(userId: number) {
    this.membershipService.getByUserId(userId).subscribe(
      membership => {
        if (!membership || !membership.isPresent) {
          this.router.navigate([""]);
          return;
        }
        this.authService.setMembership(membership);
        this.teamService.getById(membership.idTeam).subscribe(
          team => {
            this.authService.setTeam(team);
            this.router.navigate([""]);
          }
        )
      },
      error => {
        //TODO
        this.router.navigate([""]);
      }
    )
  }
}

