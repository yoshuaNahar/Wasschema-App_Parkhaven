import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {FormBuilder} from '@angular/forms';
import {Message} from '../notification-board.component';
import {AngularFireDatabase} from 'angularfire2/database';
import {AngularFireAuth} from 'angularfire2/auth';

@Component({
  selector: 'app-editor',
  templateUrl: './editor.component.html',
  styleUrls: ['./editor.component.css']
})
export class EditorComponent implements OnInit {

  title = '';
  content = '';

  options2 = {placeholder: 'Title', toolbar: false, toolbarTips: false, status: false};

  options = {
    placeholder: 'Type your message here',
    hideIcons: ['side-by-side', 'fullscreen']
  };

  @Output() goBackEmitter = new EventEmitter<void>();

  messagesRef;

  currentUser;

  constructor(private formBuilder: FormBuilder,
              private afDb: AngularFireDatabase,
              private afAuth: AngularFireAuth) {
  }

  ngOnInit() {
    this.afAuth.auth.onAuthStateChanged(user => {
      this.currentUser = user;
      this.messagesRef = this.afDb.database.ref(`/messages/pending/${this.currentUser.uid}`);
    });
  }

  createMessage() {
    const message: Message = {
      title: this.title,
      content: this.content,
      houseNumber: this.currentUser.displayName,
      timestamp: new Date().toJSON()
    };

    this.messagesRef.push(message).then(value => {
      console.log(value);
    }).catch(error => {
      console.log(error);
    });
  }

  goBackEvent() {
    this.goBackEmitter.emit();
  }

}
