import {Component, OnInit} from '@angular/core';
import {FormBuilder, Validators} from '@angular/forms';
import {AngularFireAuth} from 'angularfire2/auth';
import {Router} from '@angular/router';

@Component({
  selector: 'app-delete-account',
  templateUrl: './delete-account.component.html',
  styleUrls: ['./delete-account.component.css']
})
export class DeleteAccountComponent implements OnInit {

  deleteAccountForm;

  currentUser;

  constructor(private formBuilder: FormBuilder,
              private afAuth: AngularFireAuth,
              private router: Router) {
  }

  ngOnInit() {
    this.deleteAccountForm = this.formBuilder.group({
      currentPassword: ['', Validators.required],
    });

    this.afAuth.auth.onAuthStateChanged(user => {
      this.currentUser = user;
    });
  }

  deleteAccount() {
    console.log(this.deleteAccountForm);
    const currentPassword = this.deleteAccountForm.value.currentPassword;
    console.log(currentPassword);

    this.afAuth.auth.signInWithEmailAndPassword(this.currentUser.email, currentPassword).then(value => {
      console.log('email + password correct', value);

      this.afAuth.auth.currentUser.delete().then(() => {
        console.log('account deleted!');
        this.router.navigate(['/login']);
      });
    });
  }

}
