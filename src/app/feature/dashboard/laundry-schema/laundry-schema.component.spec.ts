import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LaundrySchemaComponent } from './laundry-schema.component';

describe('LaundrySchemaComponent', () => {
  let component: LaundrySchemaComponent;
  let fixture: ComponentFixture<LaundrySchemaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LaundrySchemaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LaundrySchemaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
