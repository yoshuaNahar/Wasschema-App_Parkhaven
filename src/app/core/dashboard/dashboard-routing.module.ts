import { NgModule } from '@angular/core';
import { LaundrySchemaComponent } from './laundry-schema/laundry-schema.component';
import { SettingsComponent } from './settings/settings.component';
import { DashboardComponent } from './dashboard.component';
import { NotificationBoardComponent } from './notification-board/notification-board.component';
import { RouterModule, Routes } from '@angular/router';
import { AdminComponent } from './admin/admin.component';
import { AuthGuard } from '../services/auth-guard.service';

const routes: Routes = [
  {
    path: '', component: DashboardComponent, canActivate: [AuthGuard], children: [
      {path: '', component: LaundrySchemaComponent},
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
  ]
})
export class DashboardRoutingModule {
}
