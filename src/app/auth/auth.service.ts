import { Injectable } from '@angular/core';
import { AngularFireAuth } from '@angular/fire/auth';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material';
import { AngularFirestore } from '@angular/fire/firestore';
import { LoaderService } from '../shared/loader/loader.service';

@Injectable()
export class AuthService {

  constructor(private afAuth: AngularFireAuth,
              private afStore: AngularFirestore,
              private loaderService: LoaderService,
              private router: Router,
              private http: HttpClient,
              private snackBar: MatSnackBar) {
  }

  login(email: string, password: string) {
    this.loaderService.show();

    this.afAuth.auth.signInWithEmailAndPassword(email, password)
      .then(loginData => {
        console.log('logged in ', loginData);
        this.router.navigate(['/']);
        this.loaderService.hide();
      }).catch(error => {
      console.log(error);
      this.snackBar.open('Incorrect email or password.', 'OK');
      this.loaderService.hide();
    });
  }

  rememberMe() {
    // https://firebase.google.com/docs/auth/web/auth-state-persistence
    return this.afAuth.auth.setPersistence('local');
  }

  register(user) {
    this.loaderService.show();

    this.http.post(`${environment.firebaseUrl}/signup`, user, {
      headers: {'Content-Type': 'application/json'}
    }).subscribe(() => {
      this.router.navigate(['/login']).then(() => {
        this.snackBar.open('Sign Up was successful.', 'OK');
      });
    }, error => {
      this.snackBar.open(error.error, 'OK');
      this.loaderService.hide();
    }, () => {
      this.loaderService.hide();
    });
  }

  forgotPassword(email: string) {
    this.loaderService.show();

    this.afAuth.auth.sendPasswordResetEmail(email).then(() => {
      return this.router.navigate(['/login']);
    }).then(() => {
      this.snackBar.open(`An email was sent to ${email}.`, 'OK');
      this.loaderService.hide();
    }).catch(error => {
      this.snackBar.open(error.message, 'OK');
      this.loaderService.hide();
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

  getCurrentSignedInUser() {
    return this.afAuth.auth.currentUser;
  }

}
