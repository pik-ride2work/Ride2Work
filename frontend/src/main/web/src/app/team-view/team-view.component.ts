import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Team} from "../_models/team";
import {AuthService, MembershipService, TeamService} from "../_services";
import {User} from "../_models/user";
import {MatSnackBar, MatTableDataSource} from "@angular/material";
import {Ride} from "../_models/ride";

@Component({
  selector: 'app-team-view',
  templateUrl: './team-view.component.html',
  styleUrls: ['./team-view.component.sass']
})
export class TeamViewComponent implements OnInit {
  constructor(
    private formBuilder: FormBuilder,
    private teamService: TeamService,
    private membershipService: MembershipService,
    private authService: AuthService,
    private snackBar: MatSnackBar
  ) {
  }

  options: FormGroup;
  teams: Team[] = [];
  users: User[] = [];
  loading = false;
  dataLoading = false;
  dataSource = new MatTableDataSource<User>(this.users);
  currentUser: User;
  currentTeam: Team;

  teamName: string;

  displayedColumns: string[] = ['id', 'firstName', 'lastName', 'email'];

  getControl(controlName) {
    if (this.options.controls[controlName])
      return this.options.controls[controlName].value || "";
    return "";
  }

  ngOnInit() {
    this.options = this.formBuilder.group({
      teamSelect: [this.teamName, Validators.required],
      teamName: [this.teamName, Validators.minLength(6)]
    });

    this.currentUser = this.authService.getUser();
    this.currentTeam = this.authService.getTeam();

    if (this.currentTeam)
      this.loadUsers();

    this.loadTeams();
  }

  loadUsers() {
    this.dataLoading = true;
    this.teamService.listUsers(this.currentTeam.id).subscribe(
      users => {
        this.users = users;
        this.dataSource.data = users;
      },
      error => {
        this.dataLoading = false;
      },
      () => {
        this.dataLoading = false;
      }
    )
  }

  loadTeams() {
    this.loading = true;
    this.teamService.list().subscribe(
      teams => {
        this.teams = teams;
      },
      error => {
        this.dataLoading = false;
      },
      () => {
        this.loading = false;
      }
    )
  }

  joinTeam() {
    if (this.options.get('teamSelect').invalid)
      return;

    this.loading = true;

    let team = this.getControl('teamSelect');

    this.membershipService.joinTeam(this.currentUser.id, team.id).subscribe(
      membership => {
        this.authService.setMembership(membership);
        this.authService.setTeam(team);
        location.reload();
      },
      error => {
        this.loading = false;
        this.snackBar.open("Error", "close");
      }
    )
  }

  createTeam() {
    if (this.options.get('teamName').invalid)
      return;

    this.loading = true;

    let teamName = this.getControl('teamName');
    let team = new Team(teamName);

    this.teamService.create(team, this.currentUser.id).subscribe(
      data => {
        this.membershipService.getByUserId(this.currentUser.id).subscribe(
          membership => {
            this.authService.setMembership(membership);
            this.authService.setTeam(team);
            location.reload();
          },
          error => {
            this.loading = false;
            this.snackBar.open("Error loading membership", "close");
          }
        );
      },
      error => {
        this.loading = false;
        this.snackBar.open("Error joining team", "close");
      }
    )
  }

  leaveTeam() {
    this.loading = true;
    this.membershipService.leaveTeam(this.currentUser.id).subscribe(
      data => {
        this.authService.resetMembership();
        this.authService.resetTeam();
        location.reload();
      },
      error => {
        this.loading = false;
        this.snackBar.open("Error", "close");
      }
    )
  }
}
