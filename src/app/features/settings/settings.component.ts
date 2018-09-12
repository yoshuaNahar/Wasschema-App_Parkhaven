import { Component, OnInit } from '@angular/core';
import { AngularFireMessaging } from '@angular/fire/messaging';
import { mergeMapTo } from 'rxjs/operators';
import { messaging } from 'firebase';

@Component({
  selector: 'app-settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.css']
})
export class SettingsComponent implements OnInit {

  constructor(private afMessaging: AngularFireMessaging) {
  }

  ngOnInit() {
  }

  // TODO: this issue is giving me an error https://github.com/angular/angularfire2/issues/1855
  requestPermission() {
    const _messaging = messaging();
    _messaging.onTokenRefresh = _messaging.onTokenRefresh.bind(_messaging);

    this.afMessaging.requestPermission.pipe(mergeMapTo(this.afMessaging.tokenChanges))
      .subscribe(
        (token) => {
          console.log('Permission granted! Token=', token);
        },
        error => {
          console.log('error', error);
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
