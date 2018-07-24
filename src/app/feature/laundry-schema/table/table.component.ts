import { AfterViewInit, ChangeDetectorRef, Component, Input, OnInit } from '@angular/core';
import { Appointment } from '../laundry-schema.component';
import { MatSnackBar } from '@angular/material';
import { AngularFireDatabase } from 'angularfire2/database';
import { AppointmentService } from '../../../../core/services/appointment.service';
import { AngularFireAuth } from 'angularfire2/auth';
import { DatePipe } from '@angular/common';
import { SchemaService } from '../../schema-service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-table',
  templateUrl: './table.component.html',
  styleUrls: ['./table.component.css'],
  // changeDetection: ChangeDetectionStrategy.OnPush,
})
export class TableComponent implements OnInit, AfterViewInit {

  static TIMES = ['05:30', '07:00', '08:30', '10:00', '11:30', '13:00', '14:30', '16:00', '17:30', '19:00', '20:30', '22:00', '23:30'];
  static DAYS = TableComponent.setDates();

  room;
  machineNames: string[];
  machineType;

  schema;
  machines;

  days;
  times;

  currentUserIdToken;

  private static setDates() {
    const today = new Date();
    const days = [];
    for (let i = 0; i < 3; i++) {
      const day = new Date();
      day.setDate(today.getDate() + i);
      days.push(day);
    }

    return days;
  }

  constructor(private afDb: AngularFireDatabase,
              private afAuth: AngularFireAuth,
              private appointmentService: AppointmentService,
              private datePipe: DatePipe,
              public snackBar: MatSnackBar,
              private schemaService: SchemaService,
              private cdr: ChangeDetectorRef,
              private route: ActivatedRoute) {
    this.days = TableComponent.DAYS;
    this.times = TableComponent.TIMES;
  }

  ngOnInit() {
    console.log(this.route.snapshot.params['id']);
    this.room = this.route.snapshot.params['id'];
    // TODO: table.component should have all 4 machines
    this.machineNames = ['A1', 'A2'];
    this.machineType = 'Laundrymachine';

    this.schemaService.schemaUpdated.subscribe(value => {
      this.schema = value;
      this.machines = [];

      console.log('table component');
      console.log(this.schema);
      console.log(this.room);
      console.log(this.machineNames[0]);
      console.log(this.schema[0][`${this.room}`]);

      let machine = [];
      for (let day = 0; day < 3; day++) {
        console.log('day' + day);
        console.log(this.schema[day][`${this.room}`][`${this.machineNames[0]}`]);
        machine.push(this.schema[day][`${this.room}`][`${this.machineNames[0]}`]);
      }
      this.machines.push(machine);
      machine = [];
      for (let day = 0; day < 3; day++) {
        machine.push(this.schema[day][`${this.room}`][`${this.machineNames[0]}`]);
      }
      this.machines.push(machine);

      console.log('this.machines');
      console.log(this.machines);
    });

    // // Now you have an array with of machines[{name, appointments: [[appointment array of day 1], ...]}, ...]
    // this.addAppointmentsOfOneWeekInsideMachineArray(machine, this.machineNames[machineNameIndex], machineNameIndex);

    // this.afAuth.auth.onAuthStateChanged(user => {
    //   user.getIdToken().then(idToken => {
    //     this.currentUserIdToken = idToken;
    //   });
    // });
  }

  ngAfterViewInit(): void {
    // this.cdr.detach();
  }

  makeOrRemoveAppointment(appointment: Appointment) {
    console.log(`appointment ${appointment.houseNumber} ${appointment.day} ${appointment.time.value}`);

    // testing
    appointment.houseNumber = 230;
    this.currentUserIdToken = 1;

    if (this.currentUserIdToken) {

      console.log(this.currentUserIdToken);

      const appointmentDetails = {
        machineType: this.machineType,
        machine: this.machines[appointment.machine].name,
        time: appointment.time.index,
        room: this.room,
        idToken: this.currentUserIdToken,
        date: this.datePipe.transform(this.days[appointment.day], 'yyyy-MM-dd'),
        houseNumber: appointment.houseNumber // current houseNumber on that spot
      };

      this.appointmentService.addOrRemoveAppointment(appointmentDetails).subscribe(value => {
        console.log(value);
        if (appointment.houseNumber === '-') {
          this.snackBar.open('Appointment placed!', 'Oke', {
            duration: 5000,
            verticalPosition: 'top'
          });
        } else {
          this.snackBar.open('Appointment removed!', 'Oke', {
            duration: 5000,
            verticalPosition: 'top'
          });
        }
      });
    }
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
