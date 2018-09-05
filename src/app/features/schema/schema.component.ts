import { Component, Inject, OnInit } from '@angular/core';
import { RoomInfo, SchemaService } from './schema.service';
import { MAT_BOTTOM_SHEET_DATA, MatBottomSheet } from '@angular/material';
import { AuthService } from '../../auth/auth.service';

@Component({
  selector: 'app-schema',
  templateUrl: './schema.component.html',
  styleUrls: ['./schema.component.css'],
})
export class SchemaComponent implements OnInit {

  rooms: RoomInfo[];
  counterData;

  constructor(private schemaService: SchemaService,
              private authService: AuthService,
              private bottomSheet: MatBottomSheet) {
  }

  ngOnInit(): void {
    this.rooms = this.schemaService.roomsInfo;

    this.authService.fetchUserInformation().valueChanges().subscribe((userData: any) => {
      this.counterData = userData.counter;
    });
  }

  getCounters() {
    this.bottomSheet.open(CounterSheetComponent, {data: this.counterData});
  }

}

@Component({
  selector: 'counter-sheet',
  template: `
    <mat-nav-list>
      <a mat-list-item>
        <span mat-line>This week:</span>
        <span mat-line>Laundrymachine counter: {{counter.laundrymachine}}</span>
        <span mat-line>Dryer counter: {{counter.dryer}}</span>
      </a>

      <a mat-list-item>
        <span mat-line>Next week</span>
        <span mat-line>Laundrymachine counter: {{counter.nextWeekLaundrymachine}}</span>
        <span mat-line>Dryer counter: {{counter.nextWeekDryer}}</span>
      </a>
    </mat-nav-list>`
})
export class CounterSheetComponent {

  constructor(@Inject(MAT_BOTTOM_SHEET_DATA) public counter: any) {
  }

}

