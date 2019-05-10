import { Component, OnInit, ViewChild, ElementRef, Input } from '@angular/core';

declare var H: any;

@Component({
  selector: 'here-map',
  templateUrl: './here-map.component.html',
  styleUrls: ['./here-map.component.sass']
})
export class HereMapComponent implements OnInit {
  private platform: any;
  private map: any;
  private coords = {
    lat: 52.2319,
    lng: 21.0067
  };

  @ViewChild("map")
  public mapElement: ElementRef;

  @Input()
  public width: any;

  @Input()
  public height: any;

  public constructor() {
    this.platform = new H.service.Platform({
      "app_id": "0kj8xcjkXGoLHMxl9VdZ",
      "app_code": "MOzLzlDt9X_RnAOAk8NjuA"
    });
  }

  public ngOnInit() {}

  public ngAfterViewInit() {
    let defaultLayers = this.platform.createDefaultLayers();
    this.map = new H.Map(
      this.mapElement.nativeElement,
      defaultLayers.normal.map,
      {
        zoom: 10,
        center: this.coords
      }
    );
    let behavior = new H.mapevents.Behavior(new H.mapevents.MapEvents(this.map));
    let ui = H.ui.UI.createDefault(this.map, defaultLayers);
  }

  private clearMap() {
    this.map.removeObjects(this.map.getObjects());
  }

  private addMarker(lat, lng) {
    let marker = new H.map.Marker({
      lat: lat,
      lng: lng
    });
    this.map.addObject(marker);
  }

  private addLine(points) {
    let lineString = new H.geo.LineString();

    for(let point of points){
      lineString.pushPoint({
        lat: point.latMatched,
        lng: point.lonMatched
      });
    }

    this.map.addObject(new H.map.Polyline(
      lineString, { style: { lineWidth: 4 }}
    ));
  }

  private setCenter(lat, lng) {
    this.map.setCenter({
      lat: lat,
      lng: lng
    });
    this.map.setZoom(13);
  }

  public showPoint(lat, lng){
    this.clearMap();
    this.addMarker(lat, lng);
    this.setCenter(lat, lng);
  }

  public showLine(file: string) {
    this.clearMap();

    let json = JSON.parse(file);
    let points = json.TracePoints;

    this.addLine(points);

    this.addMarker(points[0].latMatched, points[0].lonMatched);
    this.setCenter(points[0].latMatched, points[0].lonMatched);
    this.addMarker(points[points.length - 1].latMatched, points[points.length - 1].lonMatched);
  }
}
