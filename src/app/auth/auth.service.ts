import { Injectable } from '@angular/core';
import { AngularFireAuth } from '@angular/fire/auth';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material';
import { AngularFirestore } from '@angular/fire/firestore';

@Injectable()
export class AuthService {

  constructor(private afAuth: AngularFireAuth,
              private afStore: AngularFirestore,
              private router: Router,
              private http: HttpClient,
              private snackBar: MatSnackBar) {
  }

  login(email: string, password: string) {
    this.afAuth.auth.signInWithEmailAndPassword(email, password)
      .then(loginData => {
        console.log('logged in ', loginData);
        this.router.navigate(['/']);
      }).catch(error => {
      console.log(error);
      this.snackBar.open('Incorrect email or password.', 'OK');
    });
  }

  rememberMe() {
    // https://firebase.google.com/docs/auth/web/auth-state-persistence
    return this.afAuth.auth.setPersistence('local');
  }

  register(user) {
    this.http.post(`${environment.firebaseUrl}/signup`, user, {
      headers: {'Content-Type': 'application/json'}
    }).subscribe(() => {
      this.router.navigate(['/login']).then(() => {
        this.snackBar.open('Sign Up was successful.', 'OK');
      });
    }, error => {
      this.snackBar.open(error.error, 'OK');
    });
  }

  forgotPassword(email: string) {
    this.afAuth.auth.sendPasswordResetEmail(email).then(() => {
      return this.router.navigate(['/login']);
    }).then(() => {
      this.snackBar.open(`An email was sent to ${email}.`, 'OK');
    }).catch(error => {
      this.snackBar.open(error.message, 'OK');
    });
  }

  logout() {
    this.afAuth.auth.signOut().then(() => {
      return this.router.navigate(['/login']);
    });
  }

  fetchUserInformation() {
    return this.afStore.collection('users').doc(this.afAuth.auth.currentUser.displayName);
  }

  isUserLoggedIn() {
    return this.afAuth.auth.currentUser != null;
  }

  getCurrentSignedInUser() {
    return this.afAuth.auth.currentUser;
  }

}
