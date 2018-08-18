import { AfterViewInit, Component, OnInit } from '@angular/core';
import { DatePipe, TitleCasePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { AuthService } from '../../../auth/auth.service';
import { Action } from 'angularfire2/firestore';
import { SchemaService } from '../schema.service';
import { DocumentSnapshot } from 'angularfire2/firestore/interfaces';

@Component({
  selector: 'app-room',
  templateUrl: './room.component.html',
  styleUrls: ['./room.component.css'],
  // changeDetection: ChangeDetectionStrategy.OnPush,
})
export class RoomComponent implements OnInit, AfterViewInit {

  static TIMES = ['05:30', '07:00', '08:30', '10:00', '11:30', '13:00', '14:30', '16:00', '17:30', '19:00', '20:30', '22:00', '23:30'];

  room: string;

  machineMetaData: { name: string, type: string }[] = [];

  machines;

  days;
  times;

  constructor(private authService: AuthService,
              private schemaService: SchemaService,
              private datePipe: DatePipe,
              private capitalizeFirst: TitleCasePipe,
              private route: ActivatedRoute) {
    this.times = RoomComponent.TIMES; // TODO: POSSIBLY ALSO TAKE FROM THE BACKEND, BUT IT DOESN'T HAVE TO, SINCE IT'S STATIC
  }

  ngOnInit() {
    this.room = this.route.snapshot.params['id'];

    // TODO: days can be done once, and machineMetadata each route.params change
    this.schemaService.days().valueChanges().subscribe((days: {currentWeek}) => {
      this.days = days.currentWeek;
      console.log('this.days', this.days);
    });

    // This is the most important part. Based on the route (room id) the specific firebase path
    // is selected schema/room.id
    this.route.params.subscribe(room => {
      console.log('room.id', room.id);
      this.schemaService.roomDocument(room.id).valueChanges().subscribe(docData => {
        this.machines = docData;
        console.log(this.machines); // A1 { 2018-08-01: object with 14 elements, 2018-08-02: ... }
      });

      this.schemaService.machineMetaData(room.id).valueChanges().subscribe((machineMetadata: {machines}) => {
        this.machineMetaData = machineMetadata.machines;
      });
    });
  }

  ngAfterViewInit(): void {
    // this.cdr.detach();
  }

  private addAppointmentsOfOneWeekInsideMachineArray(machineAppointments, machineName, leftOrRight) {
    const machineAppointmentsSorted = [];
    const machines = {name: machineName, appointments: []};

    for (const day of this.days) {
      console.log(day);
      console.log(machineName);
      const dayString = this.datePipe.transform(day, 'dd-MM-yyyy');
      machineAppointmentsSorted.push(machineAppointments.appointments[dayString]);
    }

    machines.appointments = machineAppointmentsSorted;

    this.machines[leftOrRight] = machines;
  }

}
