import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-notification-board',
  templateUrl: './notification-board.component.html',
  styleUrls: ['./notification-board.component.css']
})
export class NotificationBoardComponent implements OnInit {

  isCreateButtonClicked: boolean;

  constructor() {
  }

  ngOnInit() {
  }

  toggleBetweenMessagesAndEditor() {
    this.isCreateButtonClicked = !this.isCreateButtonClicked;
  }

}

