import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {LoginComponent} from "./login/login.component";
import {AuthGuard} from "./_guards";
import {RegisterComponent} from "./register/register.component";
import {RidesHistoryComponent} from "./rides-history/rides-history.component";
import {EditUserComponent} from "./edit-user/edit-user.component";
import {TeamViewComponent} from "./team-view/team-view.component";

const routes: Routes = [
  {path: '', component: RidesHistoryComponent, canActivate: [AuthGuard]},
  {path: 'edit-user', component: EditUserComponent, canActivate: [AuthGuard]},
  {path: 'team-view', component: TeamViewComponent, canActivate: [AuthGuard]},
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
