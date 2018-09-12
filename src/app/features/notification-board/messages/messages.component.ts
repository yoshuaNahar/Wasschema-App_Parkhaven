import { Component, OnDestroy, OnInit } from '@angular/core';
import { map } from 'rxjs/operators';
import { Message } from '../editor/editor.component';
import { MatSnackBar } from '@angular/material';
import { Subscription } from 'rxjs/internal/Subscription';
import { AngularFirestore, DocumentChangeAction } from '@angular/fire/firestore';
import { AngularFireAuth } from '@angular/fire/auth';

@Component({
  selector: 'app-messages',
  templateUrl: './messages.component.html',
  styleUrls: ['./messages.component.css']
})
export class MessagesComponent implements OnInit, OnDestroy {

  messages: Message[];
  messagesSubscription: Subscription;

  currentUser;

  constructor(private afStore: AngularFirestore,
              private afAuth: AngularFireAuth,
              private snackBar: MatSnackBar) {
  }

  ngOnInit() {
    this.messagesSubscription = this.afStore.collection('activeMessages').snapshotChanges().pipe(
      map((docArray: DocumentChangeAction<Message>[]) => {
        return docArray.map(doc => {
          return {
            id: doc.payload.doc.id,
            ...doc.payload.doc.data() as Message
          };
        });
      })).subscribe(messages => {
      this.messages = messages;
    });

    this.afAuth.auth.onAuthStateChanged(user => {
      this.currentUser = user;
    });
  }

  ngOnDestroy(): void {
    this.messagesSubscription.unsubscribe();
  }

  removeMessage(message) {
    console.log(message);

    this.afStore.collection('activeMessages').doc(message.id).delete().then(() => {
      this.snackBar.open('Message deleted.', 'Oke');
    }).catch(error => {
      console.log(error);
    });
  }

}
