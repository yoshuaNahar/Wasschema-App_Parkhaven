import { NgModule } from '@angular/core';
import { SharedModule } from '../shared/shared.module';
import { AngularFireModule } from '@angular/fire';
import { environment } from '../../environments/environment';
import { HttpClientModule } from '@angular/common/http';
import { AuthService } from '../auth/auth.service';
import { MAT_SNACK_BAR_DEFAULT_OPTIONS } from '@angular/material';
import { AngularFirestoreModule } from '@angular/fire/firestore';
import { AngularFireMessagingModule } from '@angular/fire/messaging';

@NgModule({
  declarations: [],
  imports: [
    SharedModule,
    HttpClientModule,
    AngularFireModule.initializeApp(environment.firebaseConfig),
    AngularFirestoreModule,
    AngularFireMessagingModule
  ],
  exports: [],
  providers: [
    AuthService,
    {provide: MAT_SNACK_BAR_DEFAULT_OPTIONS, useValue: {duration: 6000, verticalPosition: 'bottom'}}
  ]
})
export class CoreModule {
}
