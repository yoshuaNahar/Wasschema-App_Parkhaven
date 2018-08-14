import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
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
    return this.afStore.collection('rooms').doc(room);
  }

  days() {
    return this.afStore.collection('rooms').doc('metadata');
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
      }).subscribe(value => {
        console.log(value);
        this.snackBar.open('Appointment removed!', 'Oke', {
          duration: 5000,
          verticalPosition: 'top'
        });
      });
    });
  }

  addAppointment(appointment: Appointment) {
    // TODO: I added day object, so in firebase functions I also need to add the day.value.
    // appointment.day = this.datePipe.transform(appointment.day.value, 'yyyy-MM-dd');

    console.log(`appointment ${appointment.houseNumber} ${appointment.day.value} ${appointment.time.value}`);

    const currentUser = this.authService.getCurrentSignedInUser();
    console.log(currentUser);

    currentUser.getIdToken(true).then(token => {
      console.log(token);
      this.http.put('http://localhost:5000/fir-531f4/us-central1/addAppointment', {
        appointment: appointment,
        jwt: token
      }).subscribe(value => {
        console.log(value);
        this.snackBar.open('Appointment placed!', 'Oke', {
          duration: 5000,
          verticalPosition: 'top'
        });
      });
    });
  }

}
