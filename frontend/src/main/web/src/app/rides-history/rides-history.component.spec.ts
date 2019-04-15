import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RidesHistoryComponent } from './rides-history.component';

describe('RidesHistoryComponent', () => {
  let component: RidesHistoryComponent;
  let fixture: ComponentFixture<RidesHistoryComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RidesHistoryComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RidesHistoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
