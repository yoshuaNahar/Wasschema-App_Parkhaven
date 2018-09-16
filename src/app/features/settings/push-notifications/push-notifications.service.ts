import { Injectable } from '@angular/core';
import { AngularFireMessaging } from '@angular/fire/messaging';
import { messaging } from 'firebase';
import { mergeMapTo, switchMap } from 'rxjs/operators';
import { AngularFirestore } from '@angular/fire/firestore';
import { AuthService } from '../../../auth/auth.service';

@Injectable()
export class PushNotificationsService {

  constructor(private afMessaging: AngularFireMessaging,
              private afFire: AngularFirestore,
              private authService: AuthService) {
  }

  // TODO: this issue is giving me an error https://github.com/angular/angularfire2/issues/1855
  requestPermission() {
    const _messaging = messaging();
    _messaging.onTokenRefresh = _messaging.onTokenRefresh.bind(_messaging);

    this.afMessaging.requestPermission.pipe(mergeMapTo(this.afMessaging.tokenChanges))
      .subscribe(token => {
          // add token to backend
          this.afFire.collection('pushTokens').doc(this.authService.getCurrentSignedInUser().displayName).set({token: token});
          console.log('Permission granted! Token=', token);
          // show snackbar that you will get notifications
          this.listen();
        },
        error => {
          console.log('error', error);
          // show snackbar that you cancelled or dismissed
          // include: you pressed deny, maybe add the
        });
  }

  listen() {
    const _messaging = messaging();
    _messaging.onMessage = _messaging.onMessage.bind(_messaging);

    this.afMessaging.messages.subscribe(message => {
      console.log('message', message);
      // TODO: show a snackbar
    });
  }

  removeToken() {
    this.afMessaging.getToken.pipe(switchMap(token => {
      return this.afMessaging.deleteToken(token);
    })).subscribe(isDeleted => {
      this.afFire.collection('pushTokens').doc(this.authService.getCurrentSignedInUser().displayName).delete();
      console.log('token deleted', isDeleted);
    });
  }

}
