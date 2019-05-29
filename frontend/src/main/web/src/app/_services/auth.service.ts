import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {User} from "../_models/user";
import {Membership} from "../_models/membership";
import {Team} from "../_models/team";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  constructor(private http: HttpClient) {
  }

  private userItem = 'currentUser';
  private membershipItem = 'userMembership';
  private teamItem = 'userTeam';

  getUser() {
    return JSON.parse(localStorage.getItem(this.userItem));
  }

  setUser(user: User) {
    localStorage.setItem(this.userItem, JSON.stringify(user));
  }

  resetUser() {
    localStorage.removeItem(this.userItem);
  }

  getMembership() {
    return JSON.parse(localStorage.getItem(this.membershipItem));
  }

  setMembership(membership: Membership) {
    localStorage.setItem(this.membershipItem, JSON.stringify(membership));
  }

  resetMembership() {
    localStorage.removeItem(this.membershipItem);
  }

  getTeam() {
    return JSON.parse(localStorage.getItem(this.teamItem));
  }

  setTeam(team: Team) {
    localStorage.setItem(this.teamItem, JSON.stringify(team));
  }

  resetTeam() {
    localStorage.removeItem(this.teamItem);
  }

  logout() {
    this.resetUser();
    this.resetMembership();
    this.resetTeam();
  }
}
