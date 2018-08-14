import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SetFavouriteLaundryRoomComponent } from './set-favourite-laundry-room.component';

describe('SetFavouriteLaundryRoomComponent', () => {
  let component: SetFavouriteLaundryRoomComponent;
  let fixture: ComponentFixture<SetFavouriteLaundryRoomComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [SetFavouriteLaundryRoomComponent]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SetFavouriteLaundryRoomComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
