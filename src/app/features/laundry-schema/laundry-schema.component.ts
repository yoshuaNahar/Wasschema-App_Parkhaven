import { ChangeDetectionStrategy, Component } from '@angular/core';

@Component({
  selector: 'app-laundry-schema',
  templateUrl: './laundry-schema.component.html',
  styleUrls: ['./laundry-schema.component.css'],
  // changeDetection: ChangeDetectionStrategy.OnPush
})
export class LaundrySchemaComponent {

  rooms = [{id: 'A', name: 'Room A'}, {id: 'B', name: 'Room B'}, {id: 'C', name: 'Room C'}];

  constructor() {
  }

}

export interface Appointment {
  day;
  time: { index, value };
  houseNumber;
  machine;
}
