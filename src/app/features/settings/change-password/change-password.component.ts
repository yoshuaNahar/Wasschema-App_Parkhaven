import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { SettingsService } from '../settings.service';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.css']
})
export class ChangePasswordComponent implements OnInit {

  changePasswordForm: FormGroup;

  constructor(private formBuilder: FormBuilder,
              private userSettingsService: SettingsService) {
  }

  ngOnInit() {
    // TODO: Add the 6 letters minimum validator
    this.changePasswordForm = this.formBuilder.group({
      newPassword: ['', Validators.required],
    });
  }

  changePassword() {
    console.log(this.changePasswordForm);
    const newPassword = this.changePasswordForm.value.newPassword;

    this.userSettingsService.changePassword(newPassword);
  }

}
