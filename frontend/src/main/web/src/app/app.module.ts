import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {AgmCoreModule} from '@agm/core';
import {AppComponent} from './app.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {HereMapComponent} from './here-map/here-map.component';
import {RidesHistoryComponent} from './rides-history/rides-history.component';
import {HomeComponent} from "./home/home.component";
import {LoginComponent} from "./login/login.component";

import {
  MatButtonModule, MatInputModule,
  MatGridListModule,
  MatIconModule, MatListModule, MatPaginatorModule,
  MatSidenavModule,
  MatTableModule,
  MatToolbarModule,
  MatSortModule, MatCardModule
} from '@angular/material';

@NgModule({
  declarations: [
    AppComponent,
    HereMapComponent,
    RidesHistoryComponent,
    HomeComponent,
    LoginComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    CommonModule,
    FormsModule,
    AgmCoreModule.forRoot({
      apiKey: 'YOUR_KEY'
    }),
    BrowserAnimationsModule,
    MatButtonModule,
    MatSidenavModule,
    MatToolbarModule,
    MatIconModule,
    MatGridListModule,
    MatTableModule,
    MatPaginatorModule,
    MatListModule,
    MatInputModule,
    MatSortModule,
    MatCardModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
