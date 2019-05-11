import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class RideService {
  constructor(private http: HttpClient) {
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

    let data = JSON.stringify({
      "records": [{
        "value": JSON.stringify(route)
      }]
    });

    console.log(data);

    return this.http.post<any>(
      `/kafka/topics/json`,
      data,
      httpOptions
    );
  }
}
