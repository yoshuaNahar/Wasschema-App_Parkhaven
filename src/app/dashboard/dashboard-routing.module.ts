import { NgModule } from '@angular/core';
import { DashboardComponent } from './dashboard.component';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from '../auth/auth-guard';
import { NotificationBoardComponent } from '../features/notification-board/notification-board.component';
import { SettingsComponent } from '../features/settings/settings.component';
import { AdminComponent } from '../features/admin/admin.component';
import { SchemaComponent } from '../features/schema/schema.component';
import { RoomComponent } from '../features/schema/table/room.component';

const routes: Routes = [
  {
    path: '', component: DashboardComponent, canActivate: [AuthGuard], children: [
      {
        path: '', component: SchemaComponent, children: [
          {path: 'room/:id', component: RoomComponent}
        ]
      },
      {path: 'notification-board', component: NotificationBoardComponent},
      {path: 'settings', component: SettingsComponent},
      {path: 'admin', component: AdminComponent},
    ]
  }
];

@NgModule({
  imports: [
    RouterModule.forChild(routes)
  ],
  exports: [
    RouterModule
  ],
  providers: [
    AuthGuard
  ]
})
export class DashboardRoutingModule {
}
