import { ChangeDetectionStrategy, Component } from '@angular/core';

@Component({
  selector: 'app-laundry-schema',
  templateUrl: './laundry-schema.component.html',
  styleUrls: ['./laundry-schema.component.css'],
  // changeDetection: ChangeDetectionStrategy.OnPush
})
export class LaundrySchemaComponent {

  constructor() {
  }

}

export interface Appointment {
  day;
  time: { index, value };
  houseNumber;
  machine;
}
