import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Team} from "../_models/team";
import {User} from "../_models/user";

@Injectable({
  providedIn: 'root'
})
export class TeamService {
  constructor(private http: HttpClient) { }

  create(team: Team, ownerId: number) {
    return this.http.post<any>(`/api/teams/${ownerId}`, team);
  }

  update(team:Team) {
    return this.http.put<any>(`/api/teams`, team);
  }

  list() {
    return this.http.get<any>(`/api/teams/all`);
  }

  getByName(name: string){
    return this.http.get<Team>(`/api/teams/byName/${name}`);
  }

  getById(id: number) {
    return this.http.get<Team>(`/api/teams/${id}`);
  }

  delete(id: number) {
    return this.http.delete(`/api/teams/${id}`);
  }

  getOwner(id: number) {
    return this.http.get<User>(`/api/teams/owner/${id}`);
  }

  listUsers(id: number) {
    return this.http.get<User[]>(`/api/teams/listUsers/${id}`);
  }
}
