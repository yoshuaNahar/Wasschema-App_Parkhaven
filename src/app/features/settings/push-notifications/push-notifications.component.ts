import { Component, Input, OnInit } from '@angular/core';
import { mergeMapTo, switchMap } from 'rxjs/operators';
import { messaging } from 'firebase';
import { AngularFireMessaging } from '@angular/fire/messaging';
import { AngularFirestore } from '@angular/fire/firestore';

@Component({
  selector: 'app-push-notifications',
  templateUrl: './push-notifications.component.html',
  styleUrls: ['./push-notifications.component.css']
})
export class PushNotificationsComponent implements OnInit {

  @Input() checked = false;

  constructor(private afMessaging: AngularFireMessaging, private afFire: AngularFirestore) {
  }

  ngOnInit() {
  }

  changeOccured(changeEvent) {
    console.log('isChangedToTrue', changeEvent);
    if (changeEvent.checked) {
      this.requestPermission();
    } else {
      this.afMessaging.getToken.pipe(switchMap(token => {
        return this.afMessaging.deleteToken(token);
      })).subscribe(isDeleted => {
        console.log('token deleted', isDeleted);
      });
    }
  }

  // TODO: this issue is giving me an error https://github.com/angular/angularfire2/issues/1855
  requestPermission() {
    const _messaging = messaging();
    _messaging.onTokenRefresh = _messaging.onTokenRefresh.bind(_messaging);

    this.afMessaging.requestPermission.pipe(mergeMapTo(this.afMessaging.tokenChanges))
    .subscribe(
      (token) => {
        this.checked = true;
        // add token to backend
        this.afFire.collection('pushTokens').doc(token).set({});
        console.log('Permission granted! Token=', token);
        // show snackbar that you will get notifications
      },
      error => {
        this.checked = false;
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
    });
  }

}
