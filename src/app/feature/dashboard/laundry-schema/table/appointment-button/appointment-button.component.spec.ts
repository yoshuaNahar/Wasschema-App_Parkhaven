import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AppointmentButtonComponent } from './appointment-button.component';

describe('AppointmentButtonComponent', () => {
  let component: AppointmentButtonComponent;
  let fixture: ComponentFixture<AppointmentButtonComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AppointmentButtonComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AppointmentButtonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
