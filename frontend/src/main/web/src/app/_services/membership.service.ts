import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class MembershipService {
  constructor(private http: HttpClient) { }
}
