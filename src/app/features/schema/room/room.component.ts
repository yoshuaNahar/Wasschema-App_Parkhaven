import { AfterViewInit, Component, OnDestroy, OnInit } from '@angular/core';
import { DatePipe, TitleCasePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { AuthService } from '../../../auth/auth.service';
import { MachineInfo, SchemaService } from '../schema.service';
import { Subscription } from 'rxjs/internal/Subscription';

@Component({
  selector: 'app-room',
  templateUrl: './room.component.html',
  styleUrls: ['./room.component.css'],
  // changeDetection: ChangeDetectionStrategy.OnPush,
})
export class RoomComponent implements OnInit, AfterViewInit, OnDestroy {

  static TIMES = ['05:30', '07:00', '08:30', '10:00', '11:30', '13:00', '14:30', '16:00', '17:30', '19:00', '20:30', '22:00', '23:30'];

  days = [];
  times = [];

  roomId: string;

  currentRoomMachinesInfo: MachineInfo[];

  machines = {};

  private daysChangedSubscription: Subscription;

  constructor(private authService: AuthService,
              private schemaService: SchemaService,
              private datePipe: DatePipe,
              private capitalizeFirst: TitleCasePipe,
              private route: ActivatedRoute) {
    this.times = RoomComponent.TIMES;
  }

  ngOnInit() {
    // If the user leaves the tab open for more than a day.. I will just have a please refresh notification
    this.days = this.schemaService.days;
    this.daysChangedSubscription = this.schemaService.daysChanged.subscribe(days => {
      this.days = days;
      console.log(this.days);
    });

    // This is the most important part. Based on the route (room id) the specific firebase path
    // is selected schema/room.id
    this.route.params.subscribe(room => {
      this.roomId = room.id;
      console.log('this.roomId', this.roomId);
      this.currentRoomMachinesInfo = this.getCurrentRoomMachinesInfo(this.schemaService.machinesInfo);

      this.machines = this.createEmptyMachinesArray();

      this.schemaService.fetchMachinesData(this.roomId, this.days[0].id, this.days[6].id).stateChanges()
        .subscribe((documentChangeAction) => {
          documentChangeAction.forEach(doc => {
            console.log(doc);
            const appointment = doc.payload.doc.data();
            if (doc.type === 'added') { // the first pull is always added
              this.machines[appointment.machine][appointment.date][appointment.time] = appointment.houseNumber;
              console.log('appointment added', appointment);
            } else { // doc.type === 'removed'
              this.machines[appointment.machine][appointment.date][appointment.time] = '-';
              console.log('appointment removed', appointment);
            }
          });
        });
    });
  }

  ngAfterViewInit(): void {
    // this.cdr.detach();
  }

  ngOnDestroy(): void {
    this.daysChangedSubscription.unsubscribe();
  }

  private createEmptyMachinesArray() {
    const machines = {};
    for (let machinesInfo of this.currentRoomMachinesInfo) {
      const machine = {};
      for (let day of this.days) {
        const dayArray = [];
        for (let time = 0; time < 13; time++) {
          dayArray.push('-');
        }
        machine[day.id] = dayArray;
      }
      machines[machinesInfo.id] = machine;
    }
    return machines;
  }

  private getCurrentRoomMachinesInfo(machinesInfo: MachineInfo[]) {
    return machinesInfo.filter(machine => machine.room.id === this.roomId);
  }

}
