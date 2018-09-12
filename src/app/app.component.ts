import { Component, OnInit } from '@angular/core';
import { mergeMapTo } from 'rxjs/operators';
import * as app from 'firebase';
import { AngularFireMessaging } from '@angular/fire/messaging';
import { environment } from '../environments/environment';
import { AngularFireModule } from '@angular/fire';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  constructor(private afMessaging: AngularFireMessaging) {
    app.initializeApp(environment.firebaseConfig);
    const _messaging = app.messaging();
    _messaging.onTokenRefresh = _messaging.onTokenRefresh.bind(_messaging);
    _messaging.onMessage = _messaging.onMessage.bind(_messaging);
  }

  ngOnInit() {
  }

  // TODO: this issue is giving me an error https://github.com/angular/angularfire2/issues/1855
  requestPermission() {
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
    this.afMessaging.messages.subscribe(message => {
      console.log('message', message);
    });

  }


}

