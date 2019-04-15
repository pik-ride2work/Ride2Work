import {Component} from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.sass']
})
export class AppComponent {
  company = "Goldman Sachs";
  userName = "Reginald";
  userSurname = "Tempman";

  fillerNav = Array.from({length: 50}, (_, i) => `Nav Item ${i + 1}`);

  ngOnInit() {
  }
}
