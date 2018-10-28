import { Component, OnInit } from '@angular/core';
import { AngularFirestore } from '@angular/fire/firestore';
import { AuthService } from '../../auth/auth.service';

@Component({
  selector: 'app-notification-board',
  templateUrl: './notification-board.component.html',
  styleUrls: ['./notification-board.component.css']
})
export class NotificationBoardComponent implements OnInit {

  isCreateButtonClicked: boolean;

  constructor(private afStore: AngularFirestore,
              private authService: AuthService) {
  }

  ngOnInit() {
    const houseNumber = this.authService.getCurrentSignedInUser().displayName;

    this.afStore.collection('publicUsersInfo').doc(houseNumber).update({isNewNotificationAvailable: false});
  }

  toggleBetweenMessagesAndEditor() {
    this.isCreateButtonClicked = !this.isCreateButtonClicked;
  }

}

