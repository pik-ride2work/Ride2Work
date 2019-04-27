import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {CommonModule} from '@angular/common';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';

import {
  MatButtonModule,
  MatInputModule,
  MatGridListModule,
  MatIconModule,
  MatListModule,
  MatPaginatorModule,
  MatSidenavModule,
  MatTableModule,
  MatToolbarModule,
  MatSortModule,
  MatCardModule,
  MatProgressSpinnerModule
} from '@angular/material';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {HereMapComponent} from './here-map/here-map.component';
import {RidesHistoryComponent} from './rides-history/rides-history.component';
import {LoginComponent} from "./login/login.component";
import {AlertComponent} from './alert/alert.component';
import {AuthGuard} from "./_guards";
import {AuthService} from "./_services";
import {ErrorInterceptor} from "./_interceptors/error.interceptor";
import {RegisterComponent} from './register/register.component';
import {MenuComponent} from './menu/menu.component';
import {EditUserComponent} from './edit-user/edit-user.component';
import { SpinnerButtonComponent } from './spinner-button/spinner-button.component';

@NgModule({
  declarations: [
    AppComponent,
    HereMapComponent,
    RidesHistoryComponent,
    LoginComponent,
    AlertComponent,
    RegisterComponent,
    MenuComponent,
    EditUserComponent,
    SpinnerButtonComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    MatButtonModule,
    MatInputModule,
    MatGridListModule,
    MatIconModule,
    MatListModule,
    MatPaginatorModule,
    MatSidenavModule,
    MatTableModule,
    MatToolbarModule,
    MatSortModule,
    MatCardModule,
    MatProgressSpinnerModule
  ],
  providers: [
    AuthGuard,
    AuthService,
    {provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true}
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
