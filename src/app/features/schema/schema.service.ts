import { Injectable, OnInit } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { MatSnackBar } from '@angular/material';
import { DatePipe } from '@angular/common';
import { AuthService } from '../../auth/auth.service';
import { AngularFirestore } from 'angularfire2/firestore';
import { map } from 'rxjs/operators';
import { Subject } from 'rxjs/internal/Subject';

@Injectable()
export class SchemaService {

  roomsInfo: RoomInfo[] = [
    {id: 'A', name: 'Room A'},
    {id: 'B', name: 'Room B'},
    {id: 'C', name: 'Room C'}
  ];

  // I need this data in firestore because I will need to confirm that the appointment is a wash or
  // dry appointment when placing it.
  machinesInfo: MachineInfo[] = [];

  // This contains an array of the days to display
  // isDisplayable starts from the current day + 7
  days: { id, isCurrentWeek, isDisplayable }[];
  daysChanged = new Subject<any[]>();

  constructor(private http: HttpClient,
              private authService: AuthService,
              private afStore: AngularFirestore,
              private snackBar: MatSnackBar,
              private datePipe: DatePipe) {
  }

  onInitFetchMachinesInfo() {
    this.afStore.firestore.app.firestore().collection('machinesInfo').get().then(querySnapshot => {
      querySnapshot.forEach(doc => {
        const data = doc.data();

        this.machinesInfo.push({
          id: doc.id,
          type: data.type,
          room: data.room
        });
      });
    });
  }

  onInitFetchDays() {
    this.afStore.collection('days').snapshotChanges().pipe(
      map(documentChangeAction => {
        return documentChangeAction.map((doc:any) => {
          return {
            id: doc.payload.doc.id,
            isCurrentWeek: doc.payload.doc.data().isCurrentWeek,
            isDisplayable: doc.payload.doc.data().isDisplayable
          };
        }).filter(day => day.isDisplayable);
      })).subscribe(days => {
      console.log(days);
      this.days = days;
      this.daysChanged.next([...this.days]);
    });
  }

  fetchMachinesData(room, startingDate, endingDate) {
    console.log('startingDate, endingDate', startingDate, endingDate);
    return this.afStore.collection('appointments',
      ref => ref.where('room', '==', room)
        .where('date', '>=', startingDate)
        .where('date', '<=', endingDate)
        .orderBy('date')
        .orderBy('time'));
  }

  removeAppointment(appointment: Appointment) {
    console.log(`appointment ${appointment.houseNumber} ${appointment.day} ${appointment.time.value}`);

    const currentUser = this.authService.getCurrentSignedInUser();
    console.log(currentUser);

    currentUser.getIdToken(true).then(token => {
      console.log(token);
      this.http.put('http://localhost:5000/fir-531f4/us-central1/removeAppointment', {
        appointment: appointment,
        jwt: token
      }).subscribe((response: { message }) => {
        console.log('response', response);
        this.snackBar.open(response.message, 'Oke');
      }, (err: HttpErrorResponse) => {
        console.log('err', err);
      });
    });
  }

  addAppointment(appointment: Appointment) {
    console.log(`appointment ${appointment.houseNumber} ${appointment.day.value} ${appointment.time.value}`);

    const currentUser = this.authService.getCurrentSignedInUser();
    console.log(currentUser);

    currentUser.getIdToken(true).then(token => {
      console.log(token);
      this.http.put('http://localhost:5000/fir-531f4/us-central1/addAppointment', {
        appointment: appointment,
        jwt: token
      }).subscribe((response: { message }) => {
        console.log('response', response);
        this.snackBar.open(response.message, 'Oke');
      }, (err: HttpErrorResponse) => {
        console.log('err', err);
      });
    });
  }

}

export interface Appointment {
  machineInfo: MachineInfo;
  day: { index: number, value: string };
  time: { index: number, value: string };
  houseNumber: string;
}

export interface RoomInfo {
  id: string;
  name: string;
}

export interface MachineInfo {
  id: string;
  room: RoomInfo;
  type: string;
}
