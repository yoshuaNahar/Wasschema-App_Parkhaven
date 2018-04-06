import {Component, EventEmitter, Input, OnChanges, OnInit, Output} from '@angular/core';
import {Appointment} from '../../laundry-schema.component';
import {AngularFireAuth} from 'angularfire2/auth';

@Component({
  selector: 'app-appointment-button',
  templateUrl: './appointment-button.component.html',
  styleUrls: ['./appointment-button.component.css']
})
export class AppointmentButtonComponent implements OnInit, OnChanges {

  @Input('appointment') appointment: Appointment;
  @Output('appointmentMade') appointmentMade = new EventEmitter<Appointment>();

  currentUserHouseNumber;

  isEmpty;
  isLeft;
  isPassed;

  private static checkIfPassed(currentAppointment: Appointment) {
    const currentDay = 0;
    if (currentAppointment.day !== currentDay) {
      return false;
    }

    const now = new Date();
    console.log('now', now);

    const timeOfAppointment = new Date(Date.parse('1/1/1970 ' + currentAppointment.time.value));
    console.log('timeOfAppointment', timeOfAppointment);

    const deadlineToPlaceAppointment = new Date(now.getTime() + (10 * 60000)); // plus 5min
    console.log('deadlineToPlaceAppointment', deadlineToPlaceAppointment);

    const parsedTimeOfAppointment = Date.parse('1/1/1970 ' + timeOfAppointment.getHours() + ':' + timeOfAppointment.getMinutes());
    const parsedDeadlineToPlaceAppointment =
      Date.parse('1/1/1970 ' + deadlineToPlaceAppointment.getHours() + ':' + deadlineToPlaceAppointment.getMinutes());

    return parsedTimeOfAppointment <= parsedDeadlineToPlaceAppointment;
  }

  constructor(private afAuth: AngularFireAuth) {
  }

  ngOnInit() {
    this.afAuth.auth.onAuthStateChanged(user => {
      this.currentUserHouseNumber = user.displayName;
    });

    this.checkFields();
  }

  ngOnChanges() {
    this.checkFields();
  }

  private checkFields() {
    // - means empty spot
    this.isEmpty = this.appointment.houseNumber === '-';
    // isLeft or isRight
    this.isLeft = this.appointment.machine % 2 === 0;
    // time passed
    this.isPassed = AppointmentButtonComponent.checkIfPassed(this.appointment);
  }

  makeOrRemoveAppointment() {
    this.appointmentMade.emit(this.appointment);
  }

}
