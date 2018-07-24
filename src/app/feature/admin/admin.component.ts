import {Component, OnInit} from '@angular/core';
import {AngularFireDatabase} from 'angularfire2/database';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {

  users = [];
  keys = [];

  constructor(private afDb: AngularFireDatabase) {
  }

  ngOnInit() {
    this.afDb.database.ref('/users').once('value', houseNumbersSnapshot => {
      const users = [];
      const usersKeys = [];

      houseNumbersSnapshot.forEach(houseNumberSnapshot => {
        console.log(houseNumberSnapshot.forEach(userSnapshot => {
          users.push(userSnapshot.val());
          return true;
        }));
        usersKeys.push(houseNumberSnapshot.key);
        return false;
      });

      this.users = users;
      this.keys = usersKeys;
    });
  }

  delete(houseNumber) {
    this.afDb.database.ref(`/users/${houseNumber}`).remove().then(value => {
      console.log(value);
    }).catch(error => {
      console.log(error);
    })
  }

}
