import {Component, OnInit} from '@angular/core';
import {AngularFireDatabase} from 'angularfire2/database';
import {AngularFireAuth} from 'angularfire2/auth';
import {MatSnackBar} from '@angular/material';

@Component({
  selector: 'app-notification-board',
  templateUrl: './notification-board.component.html',
  styleUrls: ['./notification-board.component.css']
})
export class NotificationBoardComponent implements OnInit {

  isCreateButtonClicked: boolean;
  messages;

  currentUserHouseNumber;

  activeMessagesRef = this.afDb.database.ref('/messages/active');

  constructor(private afDb: AngularFireDatabase,
              private afAuth: AngularFireAuth,
              public snackBar: MatSnackBar) {
  }

  ngOnInit() {
    this.activeMessagesRef.on('value', messagesSnapshot => {
      const messages = [];
      messagesSnapshot.forEach(messageSnapShot => {
        console.log(messageSnapShot);

        const messageKey = messageSnapShot.key;
        const messageData = messageSnapShot.val();
        messageData.key = messageKey;

        console.log(messageKey);
        console.log(messageData);
        messages.push(messageData);

        // why is this needed?
        return false;
      });
      this.messages = messages;
    });

    this.afAuth.auth.onAuthStateChanged(user => {
      this.currentUserHouseNumber = user.displayName;
      console.log('user.displayName', user.displayName);
    });
  }

  removeMessage(key) {
    console.log(key);

    this.activeMessagesRef.child(key).remove().then(value => {
      console.log(value);

      this.snackBar.open('Message deleted', 'Oke', {duration: 5000, verticalPosition: 'top'});
    }).catch(error => {
      console.log(error);
    });
  }

  toggleMessages() {
    this.isCreateButtonClicked = !this.isCreateButtonClicked;
  }

}

export interface Message {
  title;
  content;
  houseNumber;
  timestamp;
}
