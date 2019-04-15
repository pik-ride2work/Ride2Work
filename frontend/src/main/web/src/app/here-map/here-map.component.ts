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
  private marker: any;
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

  public ngOnInit() { }

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

  public showPoint(lat, lng){
    if(this.marker)
      this.map.removeObject(this.marker);
    this.coords = {
      lat: lat,
      lng: lng
    };
    this.marker = new H.map.Marker(this.coords);
    this.map.addObject(this.marker);
    this.map.setCenter(this.coords);
    this.map.setZoom(13);
  }
}
