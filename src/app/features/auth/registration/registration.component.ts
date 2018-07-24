import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {RegistrationDetails} from '../models/RegistrationDetails';
import {RegistrationService} from '../../../core/services/auth.service';
import {MatSnackBar} from '@angular/material';
import {Router} from '@angular/router';
import {AngularFireAuth} from 'angularfire2/auth';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {

  registrationForm: FormGroup;

  constructor(private formBuilder: FormBuilder,
              private authService: RegistrationService,
              private afAuth: AngularFireAuth,
              private router: Router,
              public snackBar: MatSnackBar) {
  }

  ngOnInit() {
    this.registrationForm = this.formBuilder.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      email: ['', Validators.email],
      password: ['', [Validators.required, Validators.minLength(6)]],
      houseNumber: ['', Validators.required]
    });
  }

  onFormSubmit() {
    console.log(this.registrationForm);

    const user = new RegistrationDetails();
    user.firstName = this.registrationForm.value.firstName;
    user.lastName = this.registrationForm.value.lastName;
    user.email = this.registrationForm.value.email;
    user.password = this.registrationForm.value.password;
    user.houseNumber = this.registrationForm.value.houseNumber;

    this.authService.register(user).subscribe(value => {
      console.log('value ', value);
      this.afAuth.auth.currentUser.updateProfile({displayName: user.houseNumber, photoURL: null});
      this.router.navigate(['/login']);
    }, error => {
      console.log('error', error);
      this.snackBar.open(error.error, 'Oke', {duration: 5000, verticalPosition: 'top'});
    });
  }

}
