import { AdminComponent } from './admin.component';
import { NgModule } from '@angular/core';
import { SharedModule } from '../../shared/shared.module';

@NgModule({
  declarations: [
    AdminComponent,
  ],
  imports: [
    SharedModule,
  ],
  providers: []
})
export class AdminModule {
}
