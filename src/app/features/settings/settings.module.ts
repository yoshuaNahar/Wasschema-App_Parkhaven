import { NgModule } from '@angular/core';
import { SharedModule } from '../../shared/shared.module';
import { DeleteAccountComponent } from './delete-account/delete-account.component';
import { ChangePasswordComponent } from './change-password/change-password.component';
import { SettingsComponent } from './settings.component';
import { SetFavouriteLaundryRoomComponent } from './set-favourite-laundry-room/set-favourite-laundry-room.component';
import { SettingsService } from './settings.service';

@NgModule({
  declarations: [
    SettingsComponent,
    SetFavouriteLaundryRoomComponent,
    ChangePasswordComponent,
    DeleteAccountComponent,
  ],
  imports: [
    SharedModule,
  ],
  providers: [
    SettingsService
  ]
})
export class SettingsModule {
}
