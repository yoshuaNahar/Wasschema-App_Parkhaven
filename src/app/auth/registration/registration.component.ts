import { Component, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  FormGroupDirective,
  NgForm,
  Validators
} from '@angular/forms';
import { AuthService } from '../auth.service';
import { ErrorStateMatcher } from '@angular/material';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {

  registrationForm: FormGroup;

  // https://stackoverflow.com/questions/47884655/display-custom-validator-error-with-mat-error
  // https://stackoverflow.com/questions/47544505/angular-material-mat-error-cannot-show-message/47670892#47670892
  errorStateMatcher = new MyErrorStateMatcher();

  constructor(private formBuilder: FormBuilder,
              private authService: AuthService) {
  }

  ngOnInit() {
    this.registrationForm = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email]],
      passwords: this.formBuilder.group({
        password: ['', [Validators.required, Validators.minLength(6)]],
        confirmPassword: [''],
      }, {validator: RegistrationComponent.identicalValidator}),
      houseNumber: ['', Validators.required]
    });
  }

  onFormSubmit() {
    const user = this.registrationForm.value;
    user.password = this.password.value;

    this.authService.register(user);
  }

  static identicalValidator(group: FormGroup) {
    if (group.get('password').value !== group.get('confirmPassword').value) {
      return {identical: true};
    }
    return null;
  }

  get email() {
    return this.registrationForm.get('email');
  }

  get passwords() {
    return this.registrationForm.get('passwords');
  }

  get password() {
    return this.registrationForm.get('passwords.password');
  }

  get confirmPassword() {
    return this.registrationForm.get('passwords.confirmPassword');
  }

  get houseNumber() {
    return this.registrationForm.get('houseNumber');
  }

}

export class MyErrorStateMatcher implements ErrorStateMatcher {

  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const invalidControl = control && control.invalid;
    const invalidParent = control && control.parent && control.dirty && control.parent.invalid;

    // true means show error
    return invalidControl || invalidParent;
  }

}
