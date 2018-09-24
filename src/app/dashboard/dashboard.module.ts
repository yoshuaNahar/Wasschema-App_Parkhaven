import { NgModule } from '@angular/core';
import { DashboardRoutingModule } from './dashboard-routing.module';
import { SharedModule } from '../shared/shared.module';
import { DatePipe, TitleCasePipe } from '@angular/common';
import { SchemaModule } from '../features/schema/schema.module';
import { SettingsModule } from '../features/settings/settings.module';
import { NotificationBoardModule } from '../features/notification-board/notification-board.module';
import { AdminModule } from '../features/admin/admin.module';
import { DashboardComponent } from './dashboard.component';
import { AngularFireMessagingModule } from '@angular/fire/messaging';
import { DashboardService } from './dashboard.service';
import { LoaderComponent } from '../shared/loader/loader.component';
import { LoaderService } from '../shared/loader/loader.service';

@NgModule({
  declarations: [
    DashboardComponent,
    LoaderComponent,
  ],
  imports: [
    SharedModule,
    DashboardRoutingModule,
    SchemaModule,
    SettingsModule,
    NotificationBoardModule,
    AdminModule,

    AngularFireMessagingModule
  ],
  exports: [
    LoaderComponent,
  ],
  providers: [
    DashboardService,
    LoaderService,
    DatePipe,
    TitleCasePipe,
  ]
})
export class DashboardModule {
}
