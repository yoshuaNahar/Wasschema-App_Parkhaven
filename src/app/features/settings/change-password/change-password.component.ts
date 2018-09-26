import { Component, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  FormGroupDirective,
  NgForm,
  Validators
} from '@angular/forms';
import { SettingsService } from '../settings.service';
import { ErrorStateMatcher } from '@angular/material';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.css']
})
export class ChangePasswordComponent implements OnInit {

  changePasswordForm: FormGroup;

  errorStateMatcher = new MyErrorStateMatcher();

  constructor(private formBuilder: FormBuilder,
              private userSettingsService: SettingsService) {
  }

  static identicalValidator(group: FormGroup) {
    if (group.get('newPassword').value !== group.get('confirmPassword').value) {
      return {identical: true};
    }
    return null;
  }

  ngOnInit() {
    this.changePasswordForm = this.formBuilder.group({
      newPassword: ['', [Validators.required, Validators.minLength(6)]],
      confirmPassword: [''],
    }, {validator: ChangePasswordComponent.identicalValidator});
  }

  changePassword() {
    const passwords = this.changePasswordForm.value;

    if (passwords.newPassword === passwords.confirmPassword) {
      this.userSettingsService.changePassword(passwords.newPassword);
    }
  }

  get newPassword() {
    return this.changePasswordForm.get('newPassword');
  }

  get confirmPassword() {
    return this.changePasswordForm.get('confirmPassword');
  }

}

export class MyErrorStateMatcher implements ErrorStateMatcher {

  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    // true means show error
    return control && control.parent && control.dirty && control.parent.invalid;
  }

}
