import { NgModule } from '@angular/core';
import { SharedModule } from '../../shared/shared.module';
import { DeleteAccountComponent } from './delete-account/delete-account.component';
import { ChangePasswordComponent } from './change-password/change-password.component';
import { SettingsComponent } from './settings.component';
import { SetFavouriteLaundryRoomComponent } from './set-favourite-laundry-room/set-favourite-laundry-room.component';
import { SettingsService } from './settings.service';
import { PushNotificationsComponent } from './push-notifications/push-notifications.component';
import { PushNotificationsService } from './push-notifications/push-notifications.service';

@NgModule({
  declarations: [
    SettingsComponent,
    SetFavouriteLaundryRoomComponent,
    ChangePasswordComponent,
    DeleteAccountComponent,
    PushNotificationsComponent,
  ],
  imports: [
    SharedModule,
  ],
  providers: [
    SettingsService,
    PushNotificationsService
  ]
})
export class SettingsModule {
}
