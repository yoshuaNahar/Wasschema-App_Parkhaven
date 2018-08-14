import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { SettingsService } from '../settings.service';

@Component({
  selector: 'app-delete-account',
  templateUrl: './delete-account.component.html',
  styleUrls: ['./delete-account.component.css']
})
export class DeleteAccountComponent implements OnInit {

  deleteAccountForm;

  constructor(private formBuilder: FormBuilder,
              private userSettingsService: SettingsService) {
  }

  ngOnInit() {
    this.deleteAccountForm = this.formBuilder.group({
      currentPassword: ['', Validators.required],
    });
  }

  deleteAccount() {
    console.log(this.deleteAccountForm);
    const currentPassword = this.deleteAccountForm.value.currentPassword;
    console.log(currentPassword);

    this.userSettingsService.deleteAccount();
  }

}
