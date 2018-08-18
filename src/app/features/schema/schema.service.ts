import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Appointment } from './schema.component';
import { MatSnackBar } from '@angular/material';
import { DatePipe } from '@angular/common';
import { AuthService } from '../../auth/auth.service';
import { AngularFirestore } from 'angularfire2/firestore';

@Injectable()
export class SchemaService {

  constructor(private http: HttpClient,
              private authService: AuthService,
              private afStore: AngularFirestore,
              private snackBar: MatSnackBar,
              private datePipe: DatePipe) {
  }

  roomDocument(room) {
    return this.afStore.collection('rooms', ref => ref.limit(2)).doc(room);
  }

  days() {
    return this.afStore.collection('roomsMetadata').doc('days');
  }

  machineMetaData(room) {
    return this.afStore.collection('roomsMetadata').doc(room);
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
