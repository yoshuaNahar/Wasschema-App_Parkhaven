import { Component, OnDestroy, OnInit } from '@angular/core';
import { AngularFirestore, DocumentChangeAction, } from 'angularfire2/firestore';
import { map } from 'rxjs/operators';
import { Subscription } from 'rxjs/internal/Subscription';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit, OnDestroy {

  users = [];
  private usersSubscription: Subscription;

  constructor(private afStore: AngularFirestore) {
  }

  ngOnInit() {
    this.usersSubscription = this.afStore.collection('users').snapshotChanges().pipe(
      map((docArray: DocumentChangeAction<object>[]) => {
        return docArray.map(doc => {
          return {
            id: doc.payload.doc.id,
            ...doc.payload.doc.data()
          };
        });
      })).subscribe(users => {
      console.log('users', users);
      this.users = users;
    })
  }

  ngOnDestroy(): void {
    this.usersSubscription.unsubscribe();
  }

  delete(user) {
    this.afStore.collection('users').doc(user.id).delete().then(value => {
      console.log(value);
      this.afStore.collection('houseNumbers').doc(user.houseNumber).update({
        user: null
      });
    });
  }

}
