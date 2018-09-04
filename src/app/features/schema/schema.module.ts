import { NgModule } from '@angular/core';
import { CounterSheetComponent, SchemaComponent } from './schema.component';
import { AppointmentButtonComponent } from './room/appointment-button/appointment-button.component';
import { SharedModule } from '../../shared/shared.module';
import { RoomComponent } from './room/room.component';
import { SchemaService } from './schema.service';

@NgModule({
  declarations: [
    SchemaComponent,
    RoomComponent,
    AppointmentButtonComponent,
    CounterSheetComponent,
  ],
  imports: [
    SharedModule,
  ],
  entryComponents: [
    CounterSheetComponent // component to start during runtime (show counters)
  ],
  providers: [
    SchemaService,
  ]
})
export class SchemaModule {
}
