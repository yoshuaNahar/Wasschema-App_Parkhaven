import { Component } from '@angular/core';

@Component({
  selector: 'app-schema',
  templateUrl: './schema.component.html',
  styleUrls: ['./schema.component.css'],
})
export class SchemaComponent {

  rooms = [
    {id: 'A', name: 'Room A'},
    {id: 'B', name: 'Room B'},
    {id: 'C', name: 'Room C'}
  ];

  constructor() {
  }

}

export interface Appointment {
  room;
  machineMetaData: { name, type };
  day;
  time: { index, value };
  houseNumber;
}
