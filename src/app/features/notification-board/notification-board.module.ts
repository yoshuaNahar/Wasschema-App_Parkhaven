import { NgModule } from '@angular/core';
import { SharedModule } from '../../shared/shared.module';
import { EditorComponent } from './editor/editor.component';
import { MessagesComponent } from './messages/messages.component';
import { NotificationBoardComponent } from './notification-board.component';
import { SIMPLEMDE_CONFIG, SimplemdeModule } from 'ng2-simplemde';

@NgModule({
  declarations: [
    NotificationBoardComponent,
    EditorComponent,
    MessagesComponent,
  ],
  imports: [
    SharedModule,
    SimplemdeModule.forRoot({
      provide: SIMPLEMDE_CONFIG,
      useValue: {}
    }),
  ],
  providers: []
})
export class NotificationBoardModule {
}
