import { NgModule } from '@angular/core';
import { SharedModule } from '../shared/shared.module';
import { AngularFireModule } from 'angularfire2';
import { environment } from '../../environments/environment';
import { HttpClientModule } from '@angular/common/http';
import { AuthService } from '../auth/auth.service';
import { MAT_SNACK_BAR_DEFAULT_OPTIONS } from '@angular/material';
import { AngularFirestoreModule } from 'angularfire2/firestore';

@NgModule({
  declarations: [],
  imports: [
    SharedModule,
    HttpClientModule,
    AngularFireModule.initializeApp(environment.firebaseConfig),
    AngularFirestoreModule,
  ],
  exports: [],
  providers: [
    AuthService,
    {provide: MAT_SNACK_BAR_DEFAULT_OPTIONS, useValue: {duration: 4000, position: 'top'}}
  ]
})
export class CoreModule {
}
