import {Component, Input, OnInit} from '@angular/core';
import {Appointment} from '../laundry-schema.component';
import {MatSnackBar} from '@angular/material';
import {AngularFireDatabase} from 'angularfire2/database';
import {AppointmentService} from '../../../../core/services/appointment.service';
import {AngularFireAuth} from 'angularfire2/auth';
import {DatePipe} from '@angular/common';

@Component({
  selector: 'app-table',
  templateUrl: './table.component.html',
  styleUrls: ['./table.component.css']
})
export class TableComponent implements OnInit {

  @Input() room;
  @Input('machine') machineNames: string[];
  @Input() machineType;

  isLoadingFirebaseDataFinished = 0;

  machines = [];
  times = ['05:30', '07:00', '08:30', '10:00', '11:30', '13:00', '14:30', '16:00', '17:30', '19:00', '20:30', '22:00', '23:30'];
  days = [];

  currentUserIdToken;

  private static setDates() {
    const today = new Date();
    const days = [];
    for (let i = 0; i < 7; i++) {
      const day = new Date();
      day.setDate(today.getDate() + i + 18); // TODO remove 25 in future also set good dates in firebase
      days.push(day);
    }

    return days;
  }

  constructor(private afDb: AngularFireDatabase,
              private afAuth: AngularFireAuth,
              private appointmentService: AppointmentService,
              private datePipe: DatePipe,
              public snackBar: MatSnackBar) {
  }

  ngOnInit() {
    this.days = TableComponent.setDates();

    for (let machineNameIndex = 0; machineNameIndex < this.machineNames.length; machineNameIndex++) {
      this.afDb.database.ref(`/rooms/${this.room}/machines/${this.machineNames[machineNameIndex]}`)
        .on('value', machine_ => {
          const machine = machine_.val();

          // // Now you have an array with of machines[{name, appointments: [[appointment array of day 1], ...]}, ...]
          this.addAppointmentsOfOneWeekInsideMachineArray(machine, this.machineNames[machineNameIndex], machineNameIndex);

          console.log(this.machines.length);
          console.log(this.machines[0].appointments.length);
          console.log(this.machines[0].appointments[0].length);

          if (this.isLoadingFirebaseDataFinished < 2) {
            this.isLoadingFirebaseDataFinished += 1;
            console.log(this.isLoadingFirebaseDataFinished);
          }
        });
    }

    this.afAuth.auth.onAuthStateChanged(user => {
      user.getIdToken().then(idToken => {
        this.currentUserIdToken = idToken;
      });
    });
  }

  makeOrRemoveAppointment(appointment: Appointment) {
    console.log(`appointment ${appointment.houseNumber} ${appointment.day} ${appointment.time.value}`);

    if (this.currentUserIdToken) {

      console.log(this.currentUserIdToken);

      const appointmentDetails = {
        machineType: this.machineType,
        machine: this.machines[appointment.machine].name,
        time: appointment.time.index,
        room: this.room,
        idToken: this.currentUserIdToken,
        date: this.datePipe.transform(this.days[appointment.day], 'dd-MM-yyyy'),
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
