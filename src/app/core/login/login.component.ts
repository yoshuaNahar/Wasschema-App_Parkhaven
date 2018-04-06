import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {MatSnackBar} from '@angular/material';
import {AngularFireAuth} from 'angularfire2/auth';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;

  // demo stuff
  signedInUser;
  showSignedInUser = false;

  constructor(private formBuilder: FormBuilder,
              private router: Router,
              private afAuth: AngularFireAuth,
              public snackBar: MatSnackBar) {
  }

  ngOnInit() {
    this.loginForm = this.formBuilder.group({
      email: ['', Validators.email],
      password: ['', Validators.required],
      rememberMe: ''
    });

    this.afAuth.auth.onAuthStateChanged(user => {
      if (user) {
        this.signedInUser = user;
      } else {
        console.log('user not signed in!');
      }
    });
  }

  login() {
    console.log(this.loginForm);
    const username = this.loginForm.value.email;
    const password = this.loginForm.value.password;
    const rememberMe = this.loginForm.value.rememberMe;
    console.log(username + ' - ' + password + ' - ' + rememberMe);

    this.afAuth.auth.signInWithEmailAndPassword(username, password)
      .then(value => {
        console.log('logged in ', value);
        this.router.navigate(['/']);
      }).catch(error => {
      console.log(error);
      this.snackBar.open('Incorrect email or password', 'Oke', {
        duration: 5000,
        verticalPosition: 'top'
      });
    });
  }

}
