import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable()
export class AppointmentService {

  constructor(private http: HttpClient) {
  }

  addOrRemoveAppointment(appointmentDetail: AppointmentDetail) {
    return this.http.put('http://localhost:5000/fir-531f4/us-central1/appointment', appointmentDetail);
  }
}

interface AppointmentDetail {
  machineType;
  date;
  room;
  machine;
  time;
  idToken;
  houseNumber;
}
