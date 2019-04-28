import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {AuthService, TeamService} from "../_services";
import {User} from "../_models/user";
import {MatDialog, MatSnackBar} from "@angular/material";
import {DialogComponent} from "../dialog/dialog.component";
import {Team} from "../_models/team";

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.sass']
})
export class MenuComponent implements OnInit {
  private currentUser: User;
  private teamName = "";

  constructor(
    private router: Router,
    private authService: AuthService,
    public dialog: MatDialog,
    private teamService: TeamService,
    public snackBar: MatSnackBar
  ) {
  }

  ngOnInit() {
    this.currentUser = this.authService.getLogged();
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

    dialogRef.afterClosed().subscribe(result => {
      if (!result)
        return;
      this.teamService.getByName(result).subscribe(
        data => {
          console.log(data);
          //TODO
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
      if(result.length < 6){
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
    this.router.navigate(["login"]);
  }
}
