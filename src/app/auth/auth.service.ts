import { Injectable } from '@angular/core';
import { AngularFireAuth } from 'angularfire2/auth';
import { HttpClient } from '@angular/common/http';
import { RegistrationDetails } from './RegistrationDetails';
import { environment } from '../../environments/environment';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material';

@Injectable()
export class AuthService {

  constructor(private afAuth: AngularFireAuth,
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
      this.snackBar.open('Incorrect email or password', 'Oke');
    });
  }

  /*
   * I need to do a housenumber check before I allow the user's profile to be saved
   */
  register(user: RegistrationDetails) {
    this.http.post(`${environment.firebaseUrl}/signup`, user, {
      headers: {'Content-Type': 'application/json'}
    }).subscribe(value => {
      console.log('value ', value);
      this.afAuth.auth.currentUser.updateProfile({displayName: user.houseNumber, photoURL: null});
      this.router.navigate(['/login']);
    }, error => {
      console.log('error', error);
      this.snackBar.open(error.error, 'Oke');
    });
  }

  forgotPassword(email: string) {
    // send email to user account
    // navigate to login page
    // show notification that email is send
    this.afAuth.auth.sendPasswordResetEmail(email).then(() =>
      this.router.navigate(['/login']).then(() =>
        this.snackBar.open(`Email sent to ${email}`, 'Oke')));
  }

  logout() {
    this.afAuth.auth.signOut().then(() => {
      this.router.navigate(['/login']);
    })
  }

  isUserLoggedIn() {
    return this.afAuth.auth.currentUser != null;
  }

  getCurrentSignedInUser() {
    return this.afAuth.auth.currentUser;
  }

}
