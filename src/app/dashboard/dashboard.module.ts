import { NgModule } from '@angular/core';
import { DashboardRoutingModule } from './dashboard-routing.module';
import { SharedModule } from '../shared/shared.module';
import { DatePipe, TitleCasePipe } from '@angular/common';
import { SchemaModule } from '../features/schema/schema.module';
import { SettingsModule } from '../features/settings/settings.module';
import { NotificationBoardModule } from '../features/notification-board/notification-board.module';
import { AdminModule } from '../features/admin/admin.module';
import { DashboardComponent } from './dashboard.component';

@NgModule({
  declarations: [
    DashboardComponent,
  ],
  imports: [
    SharedModule,
    DashboardRoutingModule,
    SchemaModule,
    SettingsModule,
    NotificationBoardModule,
    AdminModule,
  ],
  providers: [
    DatePipe,
    TitleCasePipe,
  ]
})
export class DashboardModule {
}
