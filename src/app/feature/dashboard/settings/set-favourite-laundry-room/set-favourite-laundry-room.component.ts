import {Component, OnInit} from '@angular/core';
import {AngularFireDatabase} from 'angularfire2/database';
import {FormBuilder, FormGroup} from '@angular/forms';
import {AngularFireAuth} from 'angularfire2/auth';
import {MatSnackBar} from '@angular/material';

@Component({
  selector: 'app-set-favourite-laundry-room',
  templateUrl: './set-favourite-laundry-room.component.html',
  styleUrls: ['./set-favourite-laundry-room.component.css']
})
export class SetFavouriteLaundryRoomComponent implements OnInit {

  setFavouriteLaundryRoomForm: FormGroup;

  favouriteRoomRef;

  currentUser;

  favouriteLaundryRoom;

  constructor(private formBuilder: FormBuilder,
              private afAuth: AngularFireAuth,
              private afDb: AngularFireDatabase,
              public snackBar: MatSnackBar) {
  }

  ngOnInit() {
    this.setFavouriteLaundryRoomForm = this.formBuilder.group({
      favouriteLaundryRoom: ['']
    });

    this.afAuth.auth.onAuthStateChanged(user => {
      this.currentUser = user;
      this.favouriteRoomRef = this.afDb.database.ref(`/users/${this.currentUser.displayName}/${this.currentUser.uid}/favouriteRoom`);

      this.favouriteRoomRef.once('value', favouriteLaundryRoom_ => {
        this.favouriteLaundryRoom = favouriteLaundryRoom_.val();
        console.log(this.favouriteLaundryRoom);
      });
    });
  }

  setFavouriteLaundryRoom() {
    console.log(this.setFavouriteLaundryRoomForm);
    const favouriteLaundryRoom = this.setFavouriteLaundryRoomForm.value.favouriteLaundryRoom;
    console.log(favouriteLaundryRoom);

    this.favouriteRoomRef.set(favouriteLaundryRoom)
      .then(value => {
        console.log('favLaundryRoomSet', value);
        this.snackBar.open('Favourite laundry room set', 'Oke', {
          duration: 5000,
          verticalPosition: 'top'
        });
      });
  }

}
