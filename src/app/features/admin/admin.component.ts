import { Component, OnDestroy, OnInit } from '@angular/core';
import { AngularFirestore, DocumentChangeAction, } from '@angular/fire/firestore';
import { map } from 'rxjs/operators';
import { Subscription } from 'rxjs/internal/Subscription';
import {
  MatListOption,
  MatSelectChange,
  MatSelectionListChange,
  MatSnackBar
} from '@angular/material';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { AuthService } from '../../auth/auth.service';
import { SchemaService } from '../schema/schema.service';
import { FormBuilder, FormGroup } from '@angular/forms';
import { environment } from '../../../environments/environment';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit, OnDestroy {

  times;
  rooms;
  machines;

  // all 12 machines
  allMachines;

  selectedMachines;

  users = [];
  private usersSubscription: Subscription;

  addMaintenanceForm: FormGroup;

  constructor(private afStore: AngularFirestore,
              private authService: AuthService,
              private http: HttpClient,
              private schemaService: SchemaService,
              private formBuilder: FormBuilder,
              private snackBar: MatSnackBar) {
  }

  ngOnInit() {
    this.times = this.schemaService.times;
    this.rooms = this.schemaService.roomsInfo;

    this.schemaService.onInitFetchMachinesInfo().then(machinesQuery => {
      machinesQuery.forEach(doc => {
        const data = doc.data();

        this.allMachines.push({
          id: doc.id,
          type: data.type,
          room: data.room,
          color: data.color
        });
      });
    });

    this.usersSubscription = this.afStore.collection('users').snapshotChanges().pipe(
      map((docArray: DocumentChangeAction<object>[]) => {
        return docArray.map(doc => {
          return {
            id: doc.payload.doc.id,
            ...doc.payload.doc.data()
          };
        }).filter((user: any) => {
          return user.email; // if user.email is defined, an account exists.
        });
      })).subscribe(users => {
      this.users = users;
    });

    this.addMaintenanceForm = this.formBuilder.group({
      startDate: [''],
      startTime: [''],
      endDate: [''],
      endTime: [''],
      room: ['']
    });
  }

  ngOnDestroy(): void {
    this.usersSubscription.unsubscribe();
  }

  // made a function because I can't delete accounts from the web api
  delete(user) {
    const currentUser = this.authService.getCurrentSignedInUser();

    currentUser.getIdToken(true).then(token => {
      return this.http.put(`${environment.firebaseUrl}/deleteUser`, {
        userToDelete: user,
        jwt: token
      }).toPromise();
    }).then((response: any) => {
      this.snackBar.open(response.message, 'OK');
    }).catch((httpErrorResponse: HttpErrorResponse) => {
      this.snackBar.open(httpErrorResponse.error.message, 'OK');
    });
  }

  addMaintenance() {
    const maintenance = this.addMaintenanceForm.value;
    maintenance.machines = this.selectedMachines;
    console.log('maintenance', maintenance);

    this.afStore.collection('maintenances').add(maintenance).then(() => {
      this.snackBar.open('Maintenance added.', 'OK');
    });
  }

  setMachinesForRoom(selected: MatSelectChange) {
    this.machines = this.allMachines.filter(machine => {
      return machine.room.id === selected.value;
    });
  }

  selectMachine(selected: MatSelectionListChange) {
    this.selectedMachines = [];

    selected.source.selectedOptions.selected.forEach((option: MatListOption) => {
      this.selectedMachines.push(option.value);
    });
  }

}
