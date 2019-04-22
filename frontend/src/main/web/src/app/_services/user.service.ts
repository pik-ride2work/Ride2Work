import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {User} from "../_models/user";

@Injectable({
  providedIn: 'root'
})
export class UserService {
  constructor(private http: HttpClient) { }

  get(username: String) {
    return this.http.get<User>(`http://localhost:8080/users/` + username);
  }

  register(user: User) {
    return this.http.post<User>(`http://localhost:8080/users/`, user);
  }
}
