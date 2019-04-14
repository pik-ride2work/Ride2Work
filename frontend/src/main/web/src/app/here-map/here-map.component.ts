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

  @ViewChild("map")
  public mapElement: ElementRef;

  @Input()
  public lat: any;

  @Input()
  public lng: any;

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
        center: { lat: this.lat, lng: this.lng }
      }
    );
    let behavior = new H.mapevents.Behavior(new H.mapevents.MapEvents(this.map));
    let ui = H.ui.UI.createDefault(this.map, defaultLayers);
  }

  public showPoint(lat, lng){
    this.map.setCenter({
      lat: lat,
      lng: lng
    });
  }

}
