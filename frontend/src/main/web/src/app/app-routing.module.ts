import {NgModule} from '@angular/core';
import {Routes, RouterModule, ActivatedRouteSnapshot, RouterStateSnapshot} from '@angular/router';
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
  {
    path: 'swagger',
    component: LoginComponent,
    resolve: {
      url: 'externalUrlRedirectResolver'
    },
    data: {
      externalUrl: 'http://localhost:8080/swagger-ui.html#/'
    }
  },
  {path: '**', redirectTo: 'login'}
];

@NgModule({
  providers: [
    {
      provide: 'externalUrlRedirectResolver',
      useValue: (route: ActivatedRouteSnapshot, state: RouterStateSnapshot) => {
        window.location.href = (route.data as any).externalUrl;
      }
    }
  ],
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
