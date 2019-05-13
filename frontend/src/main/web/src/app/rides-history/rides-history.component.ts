import {Component, OnInit, ViewChild} from '@angular/core';
import {MatPaginator, MatSort, MatTableDataSource} from "@angular/material";
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

  constructor(
    private authService: AuthService,
    private rideService: RideService
  ) { }

  ngOnInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;

    this.currentUser = this.authService.getUser();
    this.rideService.getRoutesByUserId(this.currentUser.id).subscribe(
      routes => {
        this.userRides = routes;
        this.dataSource.data = this.userRides;
      },
      error => {
        console.log(error);
      }
    )
  }
}
