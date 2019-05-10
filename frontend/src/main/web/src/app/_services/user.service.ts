import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {User} from "../_models/user";

@Injectable({
  providedIn: 'root'
})
export class UserService {
  constructor(private http: HttpClient) { }

  get(username: String) {
    return this.http.get<User>(`/api/users/${username}`);
  }

  create(user: User) {
    return this.http.post<User>(`/api/users/`, user);
  }

  update(user: User) {
    return this.http.put<User>(`/api/users/`, user);
  }
}
