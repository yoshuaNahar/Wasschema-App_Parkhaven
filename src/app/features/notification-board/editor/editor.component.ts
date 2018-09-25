import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { MatSnackBar } from '@angular/material';
import { AngularFirestore } from '@angular/fire/firestore';
import { AuthService } from '../../../auth/auth.service';

@Component({
  selector: 'app-editor',
  templateUrl: './editor.component.html',
  styleUrls: ['./editor.component.css']
})
export class EditorComponent implements OnInit {

  title = '';
  content = '';

  titleOptions = {
    placeholder: 'Title',
    toolbar: false,
    toolbarTips: false,
    status: false
  };

  contentOptions = {
    placeholder: 'Type your message here',
    hideIcons: ['side-by-side', 'fullscreen']
  };

  currentUser;

  @Output() private goBackEmitter = new EventEmitter<void>();

  constructor(private formBuilder: FormBuilder,
              private afStore: AngularFirestore,
              private authService: AuthService,
              private snackBar: MatSnackBar) {
  }

  ngOnInit() {
    this.currentUser = this.authService.getCurrentSignedInUser();
  }

  createMessage() {
    const message: Message = {
      title: this.title,
      content: this.content,
      houseNumber: this.currentUser.displayName,
      created: new Date()
    };

    this.afStore.collection('pendingMessages').add(message).then(value => {
      console.log(value);
      this.goBackEvent();
      this.snackBar.open('Message created.', 'OK');
    }).catch(error => {
      console.log(error);
    });
  }

  goBackEvent() {
    this.goBackEmitter.emit();
  }

}

export interface Message {
  id?;
  title;
  content;
  houseNumber;
  activated?;
  created?;
  pending?;
}
