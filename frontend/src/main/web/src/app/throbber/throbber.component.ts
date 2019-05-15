import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-throbber',
  templateUrl: './throbber.component.html',
  styleUrls: ['./throbber.component.sass']
})
export class ThrobberComponent implements OnInit {

  @Input()
  public visible: boolean;

  constructor() { }

  ngOnInit() {
  }

}
