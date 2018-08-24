import { NgModule } from '@angular/core';
import { SchemaComponent } from './schema.component';
import { AppointmentButtonComponent } from './room/appointment-button/appointment-button.component';
import { SharedModule } from '../../shared/shared.module';
import { RoomComponent } from './room/room.component';
import { SchemaService } from './schema.service';

@NgModule({
  declarations: [
    SchemaComponent,
    RoomComponent,
    AppointmentButtonComponent,
  ],
  imports: [
    SharedModule,
  ],
  providers: [
    SchemaService,
  ]
})
export class SchemaModule {
}
