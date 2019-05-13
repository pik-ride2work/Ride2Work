import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {RoutePoint} from "../_models/route-point";
import {Ride} from "../_models/ride";

@Injectable({
  providedIn: 'root'
})
export class RideService {
  constructor(private http: HttpClient) {
  }

  getPointsByRouteId(routeId) {
    return this.http.get<RoutePoint[]>(`api/routes/points/${routeId}`);
  }

  getRoutesByUserId(userId) {
    return this.http.get<Ride[]>(`api/routes/${userId}`);
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
