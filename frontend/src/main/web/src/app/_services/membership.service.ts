import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Membership} from "../_models/membership";

@Injectable({
  providedIn: 'root'
})
export class MembershipService {
  constructor(private http: HttpClient) {
  }

  joinTeam(userId: number, teamId: number) {
    return this.http.post<any>(`/api/memberships?userId=${userId}&teamId=${teamId}`, null);
  }

  leaveTeam(userId: number) {
    return this.http.delete<any>(`/api/memberships?userId=${userId}`, null);
  }

  getByUserId(userId: number) {
    return this.http.get<Membership>(`/api/memberships/${userId}`);
  }
}
