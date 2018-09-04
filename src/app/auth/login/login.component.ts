import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AngularFireAuth } from 'angularfire2/auth';
import { AuthService } from '../auth.service';
import { AngularFirestore } from 'angularfire2/firestore';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;

  constructor(private formBuilder: FormBuilder,
              private authService: AuthService,
              private afStore: AngularFirestore,
              private afAuth: AngularFireAuth) {
  }

  ngOnInit() {
    this.loginForm = this.formBuilder.group({
      email: ['', Validators.email],
      password: ['', Validators.required],
      rememberMe: ''
    });
  }

  login() {
    console.log(this.loginForm);
    const username = this.loginForm.value.email;
    const password = this.loginForm.value.password;
    const rememberMe = this.loginForm.value.rememberMe;
    console.log(username + ' - ' + password + ' - ' + rememberMe);

    this.authService.login(username, password);
  }

  demoTestUsersCollectionSecurityRules() {
    this.afAuth.auth.signInWithEmailAndPassword('yosh.nahar@gmail.com', 'compaq').then(() => {
      this.afStore.collection('users').doc('401').valueChanges().subscribe(user => {
        console.log('user', user);
        console.log(this.afAuth.auth.currentUser);
      });
    });
  }

}
