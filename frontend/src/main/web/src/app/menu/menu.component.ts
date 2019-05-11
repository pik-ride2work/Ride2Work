import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {AuthService, MembershipService, RideService, TeamService} from "../_services";
import {User} from "../_models/user";
import {MatDialog, MatSnackBar} from "@angular/material";
import {DialogComponent} from "../dialog/dialog.component";
import {Team} from "../_models/team";
import {Membership} from "../_models/membership";
import * as JSZip from 'jszip';
import {HttpClient, HttpHeaders} from "@angular/common/http";

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
    public dialog: MatDialog,
    private teamService: TeamService,
    public snackBar: MatSnackBar,
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
    if (this.authService.getMembership())
      this.userMembership = this.authService.getMembership();
    else {
      this.membershipService.getByUserId(this.currentUser.id).subscribe(
        membership => {
          this.authService.setMembership(membership);
          this.loadTeam();
        },
        error => {
          console.log(error);
        }
      )
    }

    if (this.authService.getTeam())
      this.userTeam = this.authService.getTeam();
    else if (!!this.userMembership) {
      this.teamService.getById(this.userMembership.idTeam).subscribe(
        team => {
          this.userTeam = team;
          this.authService.setTeam(team);
        },
        error => {
          console.log(error);
        }
      )
    }
  }

  joinTeam() {
    const dialogRef = this.dialog.open(DialogComponent, {
      width: '250px',
      data: {
        title: "Join team",
        label: "Team name",
        value: "",
        cancelText: "Cancel",
        acceptText: "Join"
      }
    });

    dialogRef.afterClosed().subscribe(teamName => {
      if (!teamName)
        return;
      this.teamService.getByName(teamName).subscribe(
        team => {
          this.membershipService.joinTeam(this.currentUser.id, team.id).subscribe(
            result => {
              this.snackBar.open("Team joined", "close");
              this.authService.setMembership(result);
              this.authService.setTeam(team);
              this.loadTeam();
            },
            error => {
              console.log(error);
            }
          )
        },
        error => {
          this.snackBar.open("Team not found", "close");
        }
      )
    });
  }

  createTeam() {
    const dialogRef = this.dialog.open(DialogComponent, {
      width: '250px',
      data: {
        title: "Create team",
        label: "Team name",
        value: "",
        cancelText: "Cancel",
        acceptText: "Create"
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (!result)
        return;
      if (result.length < 6) {
        this.snackBar.open("Teams name should be 6-32 characters long", "close");
        return;
      }
      let team = new Team(result);
      this.teamService.create(team, this.currentUser.id).subscribe(
        data => {
          this.snackBar.open("Team created", "close");
          console.log(data);
          //TODO
        },
        error => {
          this.snackBar.open("Error", "close");
        }
      )
    });
  }

  logout() {
    this.authService.logout();
    this.router.navigate(["setUser"]);
  }

  uploadRide(file) {
    const inputNode: any = document.querySelector('#upload-file');

    if (typeof (FileReader) !== 'undefined') {
      let gpxFile: String;
      const reader = new FileReader();

      reader.onload = (e: any) => {
        gpxFile = e.target.result;
        this.rideService.getRouteFromGpx(gpxFile).subscribe(
          route => {
            route.userId = 213931;
            this.rideService.sendRouteToKafka(route).subscribe(
              data => {
                console.log(data);
              },
              error => {
                console.log(error);
              }
            )
          },
          error => {
            console.log(error);
          }
        );
      };

      reader.readAsBinaryString(inputNode.files[0]);
    }
  }
}
