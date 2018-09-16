import { Component, OnDestroy, OnInit } from '@angular/core';
import { AngularFirestore, DocumentChangeAction, } from '@angular/fire/firestore';
import { map } from 'rxjs/operators';
import { Subscription } from 'rxjs/internal/Subscription';
import { MatSnackBar } from '@angular/material';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { AuthService } from '../../auth/auth.service';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit, OnDestroy {

  users = [];
  private usersSubscription: Subscription;

  constructor(private afStore: AngularFirestore,
              private authService: AuthService,
              private http: HttpClient,
              private snackBar: MatSnackBar) {
  }

  ngOnInit() {
    this.usersSubscription = this.afStore.collection('users').snapshotChanges().pipe(
      map((docArray: DocumentChangeAction<object>[]) => {
        return docArray.map(doc => {
          return {
            id: doc.payload.doc.id,
            ...doc.payload.doc.data()
          };
        }).filter((user: any) => {
          return user.email; // if user.email is defined, an account exists.
        });
      })).subscribe(users => {
      this.users = users;
    })
  }

  ngOnDestroy(): void {
    this.usersSubscription.unsubscribe();
  }

  delete(user) {
    const currentUser = this.authService.getCurrentSignedInUser();

    currentUser.getIdToken(true).then(token => {
      return this.http.put('http://localhost:5000/fir-531f4/us-central1/deleteUser', {
        userToDelete: user,
        jwt: token
      }).toPromise();
    }).then((response: any) => {
      this.snackBar.open(response.message, 'OK');
    }).catch((httpErrorResponse: HttpErrorResponse) => {
      this.snackBar.open(httpErrorResponse.error.message, 'OK');
    });
  }

}
