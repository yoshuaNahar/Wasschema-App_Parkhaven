import { NgModule } from '@angular/core';
import { DashboardRoutingModule } from './dashboard-routing.module';
import { NotificationBoardComponent } from './notification-board/notification-board.component';
import { AdminComponent } from './admin/admin.component';
import { SettingsComponent } from './settings/settings.component';
import { DashboardComponent } from './dashboard.component';
import { LaundrySchemaComponent } from './laundry-schema/laundry-schema.component';
import { SharedModule } from '../../shared/shared.module';
import { EditorComponent } from './notification-board/editor/editor.component';
import { SIMPLEMDE_CONFIG, SimplemdeModule } from 'ng2-simplemde';
import { AppointmentButtonComponent } from './laundry-schema/table/appointment-button/appointment-button.component';
import { TableComponent } from './laundry-schema/table/table.component';
import { ChangePasswordComponent } from './settings/change-password/change-password.component';
import { SetFavouriteLaundryRoomComponent } from './settings/set-favourite-laundry-room/set-favourite-laundry-room.component';
import { DeleteAccountComponent } from './settings/delete-account/delete-account.component';
import { MessageComponent } from './notification-board/message/message.component';
import { DatePipe } from '@angular/common';
import { SchemaService } from './schema-service';

@NgModule({
  declarations: [
    LaundrySchemaComponent,
    NotificationBoardComponent,
    SettingsComponent,
    AdminComponent,
    DashboardComponent,
    EditorComponent,
    AppointmentButtonComponent,
    TableComponent,
    ChangePasswordComponent,
    SetFavouriteLaundryRoomComponent,
    DeleteAccountComponent,
    MessageComponent,
  ],
  imports: [
    SharedModule,
    DashboardRoutingModule,
    SimplemdeModule.forRoot({
      provide: SIMPLEMDE_CONFIG,
      useValue: {}
    }),
  ],
  providers: [
    DatePipe,
    SchemaService,
  ]
})
export class DashboardModule {
}
