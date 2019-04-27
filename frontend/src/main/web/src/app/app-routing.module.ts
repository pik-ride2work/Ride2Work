import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {LoginComponent} from "./login/login.component";
import {AuthGuard} from "./_guards/auth.guard";
import {RegisterComponent} from "./register/register.component";
import {RideHistory, RidesHistoryComponent} from "./rides-history/rides-history.component";
import {EditUserComponent} from "./edit-user/edit-user.component";

const routes: Routes = [
  {path: '', component: RidesHistoryComponent, canActivate: [AuthGuard]},
  {path: 'edit-user', component: EditUserComponent, canActivate: [AuthGuard]},
  {path: 'login', component: LoginComponent},
  {path: 'register', component: RegisterComponent},
  {path: '**', redirectTo: 'login'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
