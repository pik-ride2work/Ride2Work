import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {RoutePoint} from "../_models/route-point";
import {Ride} from "../_models/ride";
import {UserScore} from "../_models/user-score";
import {TeamScore} from "../_models/team-score";

@Injectable({
  providedIn: 'root'
})
export class RideService {
  constructor(private http: HttpClient) {
  }

  private parseDate(date: Date){
    let year = date.getFullYear().toString();
    let month = date.getMonth().toString();
    let day = date.getDay().toString();
    if(date.getMonth() < 10)
      month = '0' + month;
    if(date.getDay() < 10)
      day = '0' + day;
    return `${year}-${day}-${month}`;
  }

  getPointsByRouteId(routeId) {
    return this.http.get<RoutePoint[]>(`api/routes/points/${routeId}`);
  }

  getRoutesByUserId(userId) {
    return this.http.get<Ride[]>(`api/routes/${userId}`);
  }

  getTeamScore(teamId: number, fromDate: Date, toDate: Date) {
    console.log(teamId, this.parseDate(fromDate), toDate);

    return this.http.get<TeamScore>(`api/routes/score/${teamId}/${this.parseDate(fromDate)}/${this.parseDate(toDate)}`);
  }

  getRouteFromGpx(gpxFile) {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/binary'
      })
    }

    return this.http.post<any>(
      `https://rme.api.here.com/2/matchroute.json?routemode=pedestrian&app_id=0kj8xcjkXGoLHMxl9VdZ&app_code=MOzLzlDt9X_RnAOAk8NjuA`,
      gpxFile,
      httpOptions
    );
  }

  sendRouteToKafka(route) {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/vnd.kafka.json.v1+json'
      })
    }

    let data = {
      "records": [{
        "value": route
      }]
    };

    console.log(data);

    return this.http.post<any>(
      `/kafka/topics/json`,
      data,
      httpOptions
    );
  }
}
