import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { SettingsService } from '../settings.service';

@Component({
  selector: 'app-delete-account',
  templateUrl: './delete-account.component.html',
  styleUrls: ['./delete-account.component.css']
})
export class DeleteAccountComponent implements OnInit {

  constructor(private userSettingsService: SettingsService) {
  }

  ngOnInit() {
  }

  deleteAccount() {
    this.userSettingsService.deleteAccount();
  }

}
