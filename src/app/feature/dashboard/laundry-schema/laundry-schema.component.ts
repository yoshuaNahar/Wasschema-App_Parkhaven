import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-laundry-schema',
  templateUrl: './laundry-schema.component.html',
  styleUrls: ['./laundry-schema.component.css']
})
export class LaundrySchemaComponent implements OnInit {

  currentTab = 0;

  constructor() {
  }

  ngOnInit() {
  }

  whichTab(val) {
    console.log(val);
    this.currentTab = val.index;
  }

}

export interface Appointment {
  day;
  time: { index, value };
  houseNumber;
  machine;
}
