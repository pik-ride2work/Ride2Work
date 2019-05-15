import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {AuthService, MembershipService, RideService, TeamService} from "../_services";
import {User} from "../_models/user";
import {MatDialog, MatSnackBar} from "@angular/material";
import {DialogComponent} from "../dialog/dialog.component";
import {Team} from "../_models/team";
import {Membership} from "../_models/membership";
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.sass']
})
export class MenuComponent implements OnInit {
  private currentUser: User;
  private userTeam: Team;
  private userMembership: Membership;

  constructor(
    private router: Router,
    private authService: AuthService,
    private teamService: TeamService,
    private membershipService: MembershipService,
    private http: HttpClient,
    private rideService: RideService
  ) {
  }

  ngOnInit() {
    this.currentUser = this.authService.getUser();
    this.loadTeam();
  }

  loadTeam() {
    this.userMembership = this.authService.getMembership();
    this.userTeam = this.authService.getTeam();
  }

  logout() {
    this.authService.logout();
    this.router.navigate(["setUser"]);
  }
}
