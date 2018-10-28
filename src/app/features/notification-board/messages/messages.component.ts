import { Component, OnDestroy, OnInit } from '@angular/core';
import { flatMap, map } from 'rxjs/operators';
import { Message } from '../editor/editor.component';
import { MatSnackBar } from '@angular/material';
import { Subscription } from 'rxjs/internal/Subscription';
import { AngularFirestore, DocumentChangeAction } from '@angular/fire/firestore';
import { AuthService } from '../../../auth/auth.service';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../../environments/environment';

@Component({
  selector: 'app-messages',
  templateUrl: './messages.component.html',
  styleUrls: ['./messages.component.css']
})
export class MessagesComponent implements OnInit, OnDestroy {

  messages: Message[];
  pendingMessages: Message[];

  messagesSubscription: Subscription;
  pendingMessagesSubscription: Subscription;

  currentUser;
  userData;

  constructor(private afStore: AngularFirestore,
              private authService: AuthService,
              private http: HttpClient,
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
        }).map((doc: any) => {
          doc.activated = doc.activated.toDate();
          doc.pending = false;
          return doc;
        });
      })).subscribe((messages: any) => {
      this.messages = messages;
    });

    this.pendingMessagesSubscription = this.authService.fetchUserInformation().get().pipe(
      flatMap((userInfoDoc) => {
        this.userData = userInfoDoc.data();
        return this.afStore.collection('pendingMessages').snapshotChanges();
      }),
      map((docArray: DocumentChangeAction<Message>[]) => {
        return docArray.map(doc => {
          return {
            id: doc.payload.doc.id,
            ...doc.payload.doc.data() as Message
          };
        }).map(doc => {
          doc.created = doc.created.toDate();
          doc.pending = true;
          return doc;
        });
      })
    ).subscribe(pendingMessages => {
      this.pendingMessages = pendingMessages;
    });

    this.currentUser = this.authService.getCurrentSignedInUser();
  }

  ngOnDestroy(): void {
    this.messagesSubscription.unsubscribe();
    this.pendingMessagesSubscription.unsubscribe();
  }

  removeMessage(message) {
    let collRef;
    if (message.pending) {
      collRef = this.afStore.collection('pendingMessages');
    } else {
      collRef = this.afStore.collection('activeMessages');
    }

    collRef.doc(message.id).delete().then(() => {
      this.snackBar.open('Message deleted.', 'OK');
    }).catch(error => {
      console.log(error);
    });
  }

  acceptMessage(message) {
    message.activated = new Date();

    const currentUser = this.authService.getCurrentSignedInUser();

    currentUser.getIdToken(true).then(token => {
      return Promise.all([
        this.afStore.collection('activeMessages').doc(message.id).set(message),
        this.afStore.collection('pendingMessages').doc(message.id).delete(),
        // this.http.put(`${environment.firebaseUrl}/setNewNotificationBoardMessage`, {
        this.http.put(`https://us-central1-fir-531f4.cloudfunctions.net/setNewNotificationBoardMessage`, {
          jwt: token
        }).toPromise()
      ]).then(() => {
        this.snackBar.open('Message accepted.', 'OK');
      }).catch(error => {
        console.log(error);
      });
    });
  }

}
