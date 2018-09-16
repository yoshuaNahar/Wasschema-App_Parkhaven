import { Component, Input, OnInit } from '@angular/core';
import { AuthService } from '../../../auth/auth.service';
import { PushNotificationsService } from './push-notifications.service';
import { switchMap } from 'rxjs/operators';

@Component({
  selector: 'app-push-notifications',
  templateUrl: './push-notifications.component.html',
  styleUrls: ['./push-notifications.component.css']
})
export class PushNotificationsComponent implements OnInit {

  @Input() checked = false;

  constructor(private pushService: PushNotificationsService) {
  }

  ngOnInit() {
  }

  changeOccured(changeEvent) {
    console.log('isChangedToTrue', changeEvent);
    if (changeEvent.checked) {
      this.pushService.requestPermission();
    } else {
      this.pushService.removeToken();
    }
  }

}
