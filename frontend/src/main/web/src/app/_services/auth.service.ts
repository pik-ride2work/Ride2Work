import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {map} from 'rxjs/operators';
import {User} from "../_models/user";
import {Team} from "../_models/team";
import {Membership} from "../_models/membership";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  constructor(private http: HttpClient) {
  }

  private userItem = 'currentUser';
  private membershipItem = 'userMembership';
  private teamItem = 'userTeam';

  setUser(user: User) {
    localStorage.setItem(this.userItem, JSON.stringify(user));
  }

  getUser() {
    return JSON.parse(localStorage.getItem(this.userItem));
  }

  setMembership(membership: Membership) {
    localStorage.setItem(this.membershipItem, JSON.stringify(membership));
  }

  getMembership() {
    return JSON.parse(localStorage.getItem(this.membershipItem));
  }

  setTeam(team: Team) {
    localStorage.setItem(this.teamItem, JSON.stringify(team));
  }

  getTeam() {
    return JSON.parse(localStorage.getItem(this.teamItem));
  }

  logout() {
    localStorage.removeItem(this.userItem);
    localStorage.removeItem(this.membershipItem);
    localStorage.removeItem(this.teamItem);
  }
}
