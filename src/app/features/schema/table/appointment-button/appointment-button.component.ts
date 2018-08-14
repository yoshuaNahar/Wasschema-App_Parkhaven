import { Component, Input, OnChanges, OnInit } from '@angular/core';
import { Appointment } from '../../schema.component';
import { AuthService } from '../../../../auth/auth.service';
import { SchemaService } from '../../schema.service';

@Component({
  selector: 'app-appointment-button',
  templateUrl: './appointment-button.component.html',
  styleUrls: ['./appointment-button.component.css']
})
export class AppointmentButtonComponent implements OnInit, OnChanges {

  @Input() appointment: Appointment;
  @Input() isPositionedLeft: boolean;

  isEmpty;
  isInThePast;

  currentUserHouseNumber;

  constructor(private authService: AuthService,
              private schemaService: SchemaService) {
  }

  private static checkIfIsInThePast(currentAppointment: Appointment) {
    console.log(currentAppointment.day.index);
    if (currentAppointment.day.index !== 0) {
      return false;
    }

    const now = new Date();

    const timeOfAppointment = new Date(Date.parse('1/1/1970 ' + currentAppointment.time.value));

    const deadlineToPlaceAppointment = new Date(now.getTime() + (10 * 60000)); // plus 5min

    const parsedTimeOfAppointment = Date.parse('1/1/1970 ' + timeOfAppointment.getHours() + ':' + timeOfAppointment.getMinutes());
    const parsedDeadlineToPlaceAppointment =
      Date.parse('1/1/1970 ' + deadlineToPlaceAppointment.getHours() + ':' + deadlineToPlaceAppointment.getMinutes());

    return parsedTimeOfAppointment <= parsedDeadlineToPlaceAppointment;
  }

  ngOnInit() {
    this.checkFields();

    this.currentUserHouseNumber = this.authService.getCurrentSignedInUser().displayName;
  }

  ngOnChanges() {
    this.checkFields();
  }

  private checkFields() {
    // - means empty spot
    this.isEmpty = this.appointment.houseNumber === '-';
    // time passed
    this.isInThePast = AppointmentButtonComponent.checkIfIsInThePast(this.appointment);
  }

  makeOrRemoveAppointment() {
    console.log('this.isEmpty', this.isEmpty);

    if (this.isEmpty) {
      this.schemaService.addAppointment(this.appointment);
    } else {
      this.schemaService.removeAppointment(this.appointment);
    }
  }
}
