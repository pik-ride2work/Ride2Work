import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-spinner-button',
  templateUrl: './spinner-button.component.html',
  styleUrls: ['./spinner-button.component.sass']
})
export class SpinnerButtonComponent implements OnInit {

  @Input()
  public loading: boolean;

  @Input()
  public text: string;

  @Input()
  public fontSize: any;

  constructor() { }

  ngOnInit() {
  }

}
