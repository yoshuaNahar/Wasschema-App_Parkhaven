import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AuthService } from '../../../auth/auth.service';
import { MachineInfo, SchemaService } from '../schema.service';
import { Subscription } from 'rxjs/internal/Subscription';
import { switchMap } from 'rxjs/operators';
import { LoaderService } from '../../../shared/loader/loader.service';

@Component({
  selector: 'app-room',
  templateUrl: './room.component.html',
  styleUrls: ['./room.component.css'],
  // changeDetection: ChangeDetectionStrategy.OnPush,
})
export class RoomComponent implements OnInit, OnDestroy {

  days = [];
  times = [];
  dryerTimes;

  roomId: string;

  currentRoomMachinesInfo: MachineInfo[];

  machines = {};

  private megaSubscription: Subscription;

  constructor(private authService: AuthService,
              private schemaService: SchemaService,
              private loaderService: LoaderService,
              private route: ActivatedRoute) {
  }

  ngOnInit() {
    this.loaderService.show();

    this.times = this.schemaService.times;
    this.dryerTimes = this.times.slice(1);
    this.dryerTimes.push('01:00');

    // TODO: If the userData leaves the tab open for more than a day.. I will just have a please refresh notification
    this.days = this.schemaService.days;
    for (let day of this.days) {
      if (!day.isCurrentWeek) {
        day.isStartNextWeek = true;
        break;
      }
    }

    // This is the most important part. Based on the route (room id) the specific firebase path
    // is selected schema/room.id
    this.megaSubscription = this.route.params.pipe(switchMap(roomParams => {
      this.roomId = roomParams.id;
      console.log('roomId', this.roomId);
      this.currentRoomMachinesInfo = this.getCurrentRoomMachinesInfo(this.schemaService.machinesInfo);

      this.machines = this.createEmptyMachinesArray();

      return this.schemaService.fetchMachinesData(this.roomId, this.days[0].id, this.days[6].id).stateChanges();
    })).subscribe((documentChangeAction) => {
      documentChangeAction.forEach((doc: any) => {
        // console.log(doc);
        const appointment = doc.payload.doc.data();
        if (doc.type === 'added') { // the first pull always has doc.type = added
          this.machines[appointment.machine][appointment.date][appointment.time] = appointment.houseNumber;
          // console.log('appointment added', appointment);
        } else { // doc.type === 'removed'
          this.machines[appointment.machine][appointment.date][appointment.time] = '';
          // console.log('appointment removed', appointment);
        }
      });

      this.loaderService.hide();
    });
  }

  ngOnDestroy(): void {
    this.megaSubscription.unsubscribe();

    this.loaderService.hide();
  }

  private createEmptyMachinesArray() {
    const machines = {};
    for (const machinesInfo of this.currentRoomMachinesInfo) {
      const machine = {};
      for (const day of this.days) {
        const dayArray = [];
        for (let time = 0; time < 13; time++) {
          dayArray.push('');
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

  public readableType(type: string) {
    if (type === 'laundrymachine') {
      return 'Laundry machine';
    } else {
      return 'Dryer';
    }
  }

}
