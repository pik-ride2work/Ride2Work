import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ThrobberComponent } from './throbber.component';

describe('ThrobberComponent', () => {
  let component: ThrobberComponent;
  let fixture: ComponentFixture<ThrobberComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ThrobberComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ThrobberComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
