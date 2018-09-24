import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material';
import { AngularFirestore } from '@angular/fire/firestore';
import { AuthService } from '../../auth/auth.service';

@Injectable()
export class SettingsService {

  constructor(private authService: AuthService,
              private afStore: AngularFirestore,
              private router: Router,
              private snackBar: MatSnackBar) {
  }

  // I removed the ability to delete users because that is the task of the admin.
  // I couldn't give the user update permission to change his user doc to an empty
  // object, because he could otherwise change the counters.

  changePassword(newPassword) {
    this.authService.getCurrentSignedInUser().updatePassword(newPassword).then(() => {
      this.snackBar.open('Password was changed successfully.', 'OK');
    }).catch(error => {
      this.snackBar.open(error.message, 'OK');
    });
  }

  fetchPublicUserInfoRoom() {
    const currentUser = this.authService.getCurrentSignedInUser();

    return this.afStore.collection('publicUsersInfo').doc(currentUser.displayName).get();
  }

  setFavouriteLaundryRoom(laundryRoom) {
    console.log('laundryRoomSet:', laundryRoom);
    const currentUser = this.authService.getCurrentSignedInUser();

    this.afStore.collection('publicUsersInfo').doc(currentUser.displayName).update({
      favouriteRoom: laundryRoom
    }).then(() => {
      this.snackBar.open('Favourite laundry room set.', 'OK');
    });
  }

}
