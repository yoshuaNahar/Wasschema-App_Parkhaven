import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AngularFireAuth } from '@angular/fire/auth';
import { AuthService } from '../auth.service';
import { AngularFirestore } from '@angular/fire/firestore';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;

  constructor(private formBuilder: FormBuilder,
              private authService: AuthService) {
  }

  ngOnInit() {
    this.loginForm = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required],
      rememberMe: ''
    });
  }

  login() {
    const username = this.loginForm.value.email;
    const password = this.loginForm.value.password;
    const rememberMe = this.loginForm.value.rememberMe;
    console.log(username + ' - ' + password + ' - ' + rememberMe);

    if (rememberMe) {
      this.authService.rememberMe().then(() => {
        this.authService.login(username, password);
      });
    } else {
      this.authService.login(username, password);
    }
  }

}
