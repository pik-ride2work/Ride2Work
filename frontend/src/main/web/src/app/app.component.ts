import {Component, OnInit, ViewChild} from '@angular/core';
import {MatPaginator, MatTableDataSource} from "@angular/material";

export interface RideHistory {
  id: number;
  distance: number;
  time: string;
  max_speed: number;
  avg_speed: number;
  start_time: string;
  finish_time: string;
}

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
      finish_time: new Date(+(new Date()) - Math.floor(Math.random() * 10000000000)).toISOString().substring(0, 19).replace("T", " ")
    };

    data.push(obj);
  }
  return data;
}

const ELEMENT_DATA: RideHistory[] = getData();

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.sass']
})
export class AppComponent {
  fillerNav = Array.from({length: 50}, (_, i) => `Nav Item ${i + 1}`);

  displayedColumns: string[] = ['id', 'distance', 'time', 'max_speed', 'avg_speed', 'start_time', 'finish_time'];
  dataSource = new MatTableDataSource<RideHistory>(ELEMENT_DATA);

  @ViewChild(MatPaginator) paginator: MatPaginator;

  ngOnInit() {
    this.dataSource.paginator = this.paginator;
  }
}