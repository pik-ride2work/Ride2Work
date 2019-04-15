import {Component, OnInit, ViewChild} from '@angular/core';
import {MatPaginator, MatSort, MatTableDataSource} from "@angular/material";

export interface RideHistory {
  id: number;
  distance: number;
  time: string;
  max_speed: number;
  avg_speed: number;
  start_time: string;
  finish_time: string;
}

const ELEMENT_DATA: RideHistory[] = getData();

function getData() {
  let data = [];
  for (var i = 0; i < 100; ++i) {
    let obj = {
      id: i,
      distance: Math.floor(Math.random() * (100 - 2)) + 2,
      time: Math.floor(Math.random() * 23) + ":" + Math.floor(Math.random() * 59),
      max_speed: Math.floor(Math.random() * 40),
      avg_speed: Math.floor(Math.random() * 20),
      start_time: new Date(+(new Date()) - Math.floor(Math.random() * 10000000000)).toISOString().substring(0, 19).replace("T", " "),
      finish_time: new Date(+(new Date()) - Math.floor(Math.random() * 10000000000)).toISOString().substring(0, 19).replace("T", " "),
      lat: Math.random() * 0.5 + 52,
      lng: Math.random() * 0.5 + 20.75
    };

    data.push(obj);
  }
  return data;
}

@Component({
  selector: 'app-rides-history',
  templateUrl: './rides-history.component.html',
  styleUrls: ['./rides-history.component.sass']
})
export class RidesHistoryComponent implements OnInit {
  displayedColumns: string[] = ['id', 'distance', 'time', 'max_speed', 'avg_speed', 'start_time', 'finish_time'];
  dataSource = new MatTableDataSource<RideHistory>(ELEMENT_DATA);

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor() { }

  ngOnInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

}
