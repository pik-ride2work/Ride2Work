import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Team} from "../_models/team";
import {AuthService, MembershipService, RideService, TeamService} from "../_services";
import {User} from "../_models/user";
import {MatSnackBar, MatTableDataSource} from "@angular/material";
import {Chart} from "chart.js"
import {UserScore} from "../_models/user-score";

@Component({
  selector: 'app-team-view',
  templateUrl: './team-view.component.html',
  styleUrls: ['./team-view.component.sass']
})
export class TeamViewComponent implements OnInit {
  constructor(
    private formBuilder: FormBuilder,
    private teamService: TeamService,
    private rideService: RideService,
    private membershipService: MembershipService,
    private authService: AuthService,
    private snackBar: MatSnackBar
  ) {
  }

  options: FormGroup;
  teams: Team[] = [];
  users: User[] = [];
  userScores: UserScore[] = [];
  loading = false;
  dataLoading = false;
  dataSource = new MatTableDataSource<User>(this.users);
  currentUser: User;
  currentTeam: Team;

  barChart = [];

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

    if (this.currentTeam) {
      this.loadUsers();
      this.loadScores();
    }

    let loadInBackground = !!this.currentTeam;
    this.loadTeams(loadInBackground);
  }

  createChart() {
    let topScores = this.userScores;
    topScores.sort((a: UserScore, b: UserScore) => {
      return b.score - a.score;
    });

    let labels = [];
    let data = [];
    for (let i = 0; i < 5 && i < this.userScores.length; ++i) {
      let user = this.userScores[i].user;
      labels.push(`${user.firstName} ${user.lastName}`);
      data.push(this.userScores[i].score);
    }

    this.barChart = new Chart('barChart', {
      type: 'bar',
      data: {
        labels: labels,
        datasets: [{
          label: 'Points',
          data: data,
          borderWidth: 1,
          backgroundColor: 'rgb(63, 81, 181)'
        }]
      },
      options: {
        title: {
          text: "Top users",
          display: true
        }
      }
    });
  }

  loadUsers() {
    this.dataLoading = true;
    this.teamService.listUsers(this.currentTeam.id).subscribe(
      users => {
        console.log(users);
        this.users = users;
        this.dataSource.data = users;
        this.dataLoading = false;
      },
      error => {
        this.dataLoading = false;
      }
    )
  }

  loadScores() {
    this.dataLoading = true;
    let fromDate = new Date(1990, 1,1), toDate = new Date(2020,1,1);
    this.rideService.getTeamScore(this.currentTeam.id, fromDate, toDate).subscribe(
      teamScore => {
        console.log(teamScore);
        this.userScores = teamScore.users;
        this.createChart();
        this.dataLoading = false;
      },
      error => {
        this.dataLoading = false;
      }
    )
  }


  loadTeams(background = false) {
    if (!background)
      this.loading = true;
    this.teamService.list().subscribe(
      teams => {
        this.teams = teams;
        this.loading = false;
      },
      error => {
        this.dataLoading = false;
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
