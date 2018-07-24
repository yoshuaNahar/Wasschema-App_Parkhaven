import {Component, OnInit} from '@angular/core';
import {AngularFireDatabase} from 'angularfire2/database';
import {MatSnackBar} from '@angular/material';
import {AngularFireAuth} from 'angularfire2/auth';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.css']
})
export class ChangePasswordComponent implements OnInit {

  changePasswordForm: FormGroup;

  currentUser;

  constructor(private formBuilder: FormBuilder,
              private afAuth: AngularFireAuth,
              public snackBar: MatSnackBar) {
  }

  ngOnInit() {
    this.changePasswordForm = this.formBuilder.group({
      currentPassword: ['', Validators.required],
      newPassword: ['', Validators.required],
    });

    this.afAuth.auth.onAuthStateChanged(user => {
      this.currentUser = user;
    });
  }

  changePassword() {
    console.log(this.changePasswordForm);
    const currentPassword = this.changePasswordForm.value.currentPassword;
    const newPassword = this.changePasswordForm.value.newPassword;
    console.log(currentPassword + ' - ' + newPassword);

    this.afAuth.auth.signInWithEmailAndPassword(this.currentUser.email, currentPassword).then(value => {
      console.log('email + password correct', value);

      this.afAuth.auth.currentUser.updatePassword(newPassword).then(value2 => {
        console.log('password changed', value2);
        this.snackBar.open('Password changed!', 'Oke', {
          duration: 5000,
          verticalPosition: 'top'
        });
      }).catch(error2 => {
        console.log('password not changed', error2);
        this.snackBar.open('weak password', 'Oke', {
          duration: 5000,
          verticalPosition: 'top'
        });
      });
    }).catch(error => {
      console.log('incorrect password', error);
      this.snackBar.open('Incorrect password', 'Oke', {
        duration: 5000,
        verticalPosition: 'top'
      });
    });
  }

}
