import {Component, OnInit, ViewChild} from '@angular/core';
import {MatPaginator, MatSnackBar, MatSort, MatTableDataSource} from "@angular/material";
import {AuthService, RideService} from "../_services";
import {User} from "../_models/user";
import {Ride} from "../_models/ride";
import {Observable} from "rxjs";

@Component({
  selector: 'app-rides-history',
  templateUrl: './rides-history.component.html',
  styleUrls: ['./rides-history.component.sass']
})
export class RidesHistoryComponent implements OnInit {
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  private currentUser: User;
  private userRides: Ride[] = [];

  displayedColumns: string[] = ['id', 'averageSpeed', 'maxSpeed', 'distance', 'timeInSeconds'];
  dataSource = new MatTableDataSource<Ride>(this.userRides);
  dataLoading = false;
  buttonLoading = false;

  constructor(
    private authService: AuthService,
    private rideService: RideService,
    private snackBar: MatSnackBar
  ) { }

  ngOnInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;

    this.currentUser = this.authService.getUser();
    this.loadRoutes();
  }

  loadRoutes() {
    this.dataLoading = true;
    this.rideService.getRoutesByUserId(this.currentUser.id).subscribe(
      routes => {
        this.userRides = routes;
        this.dataSource.data = this.userRides;
        this.dataLoading = false;
      },
      error => {
        this.dataLoading = false;
      }
    )
  }

  uploadRide(file) {
    this.buttonLoading = true;

    const inputNode: any = document.querySelector('#upload-file');

    if (typeof (FileReader) !== 'undefined') {
      let gpxFile: String;
      const reader = new FileReader();

      reader.onload = (e: any) => {
        gpxFile = e.target.result;
        this.rideService.getRouteFromGpx(gpxFile).subscribe(
          route => {
            route.userId = this.currentUser.id;
            this.rideService.sendRouteToKafka(route).subscribe(
              data => {
                this.buttonLoading = false;
                this.snackBar.open("Route uploaded", "close");
                this.loadRoutes();
              },
              error => {
                this.buttonLoading = false;
                this.snackBar.open("Error uploading route", "close");
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
