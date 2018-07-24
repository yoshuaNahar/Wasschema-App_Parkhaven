import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AngularFireAuth } from 'angularfire2/auth';
import { MatSnackBar } from '@angular/material';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.css']
})
export class ForgotPasswordComponent implements OnInit {

  forgotPasswordForm: FormGroup;

  constructor(private formBuilder: FormBuilder,
              private router: Router,
              private afAuth: AngularFireAuth,
              public snackBar: MatSnackBar) {
  }

  ngOnInit() {
    this.forgotPasswordForm = this.formBuilder.group({
      email: ['', Validators.required],
    });
  }

  resetPassword() {
    const email = this.forgotPasswordForm.value.email;

    // navigate to login page
    // send email to user account
    // show notification that email is send
    this.router.navigateByUrl('/login').then(() =>
      this.afAuth.auth.sendPasswordResetEmail(email).then(() =>
        this.snackBar.open(`Email sent to ${email}`, null, {duration: 3000})
      )
    );
  }

}
