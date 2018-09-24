import { NgModule } from '@angular/core';
import { SharedModule } from '../../shared/shared.module';
import { ChangePasswordComponent } from './change-password/change-password.component';
import { SettingsComponent } from './settings.component';
import { SetFavouriteLaundryRoomComponent } from './set-favourite-laundry-room/set-favourite-laundry-room.component';
import { SettingsService } from './settings.service';
import { PushNotificationsComponent } from './push-notifications/push-notifications.component';

@NgModule({
  declarations: [
    SettingsComponent,
    SetFavouriteLaundryRoomComponent,
    ChangePasswordComponent,
    PushNotificationsComponent,
  ],
  imports: [
    SharedModule,
  ],
  providers: [
    SettingsService,
  ]
})
export class SettingsModule {
}
