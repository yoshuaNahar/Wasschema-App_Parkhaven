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

  changePassword(newPassword) {
    this.authService.getCurrentSignedInUser().updatePassword(newPassword).then(() => {
      this.snackBar.open('Password was changed successfully.', 'OK');
    }).catch(error => {
      this.snackBar.open(error.message, 'OK');
    });
  }

  deleteAccount() {
    const currentUser = this.authService.getCurrentSignedInUser();

    currentUser.delete().then(() => {
      this.afStore.collection('users').doc(currentUser.displayName).set({});
      this.afStore.collection('publicUsersInfo').doc(currentUser.displayName).delete();

      this.router.navigate(['/login']).then(() => {
        this.snackBar.open('Account was deleted successfully.', 'OK');
      });
    });
  }

  fetchFavouriteLaundryRoom() {
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
