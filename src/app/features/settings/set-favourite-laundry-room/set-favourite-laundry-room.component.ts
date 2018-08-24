import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { SettingsService } from '../settings.service';
import { Subscription } from 'rxjs/internal/Subscription';

@Component({
  selector: 'app-set-favourite-laundry-room',
  templateUrl: './set-favourite-laundry-room.component.html',
  styleUrls: ['./set-favourite-laundry-room.component.css']
})
export class SetFavouriteLaundryRoomComponent implements OnInit, OnDestroy {

  setFavouriteLaundryRoomForm: FormGroup;

  favouriteLaundryRoom;
  private favouriteLaundryRoomSubscription: Subscription;

  constructor(private formBuilder: FormBuilder,
              private userSettingsService: SettingsService) {
  }

  ngOnInit() {
    this.setFavouriteLaundryRoomForm = this.formBuilder.group({
      favouriteLaundryRoom: ['']
    });

    this.favouriteLaundryRoomSubscription =
      this.userSettingsService.fetchFavouriteLaundryRoom().subscribe((user: { favouriteRoom }) => {
        console.log(user);
        this.favouriteLaundryRoom = user.favouriteRoom;

        console.log('this.favouriteLaundryRoom', this.favouriteLaundryRoom);
      });
  }

  ngOnDestroy() {
    this.favouriteLaundryRoomSubscription.unsubscribe();
  }

  setFavouriteLaundryRoom() {
    const favouriteLaundryRoom = this.setFavouriteLaundryRoomForm.value.favouriteLaundryRoom;

    this.userSettingsService.setFavouriteLaundryRoom(favouriteLaundryRoom);
  }

}
