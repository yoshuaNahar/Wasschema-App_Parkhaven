import { NgModule } from '@angular/core';
import { LoginComponent } from '../features/auth/login/login.component';
import { RegistrationComponent } from '../features/auth/registration/registration.component';
import { RegistrationService } from './services/auth.service';
import { ForgotPasswordComponent } from '../features/auth/forgot-password/forgot-password.component';
import { ReactiveFormsModule } from '@angular/forms';
import { SharedModule } from '../shared/shared.module';
import { AngularFireModule } from 'angularfire2';
import { environment } from '../../environments/environment';
import { AngularFireAuthModule } from 'angularfire2/auth';
import { HttpClientModule } from '@angular/common/http';
import { AuthGuard } from './services/auth-guard.service';
import { AngularFireDatabaseModule } from 'angularfire2/database';
import { AppointmentService } from './services/appointment.service';

@NgModule({
  declarations: [
    LoginComponent,
    RegistrationComponent,
    ForgotPasswordComponent,
  ],
  imports: [
    SharedModule,
    HttpClientModule,
    AngularFireModule.initializeApp(environment.firebase),
    AngularFireAuthModule,
    AngularFireDatabaseModule,
  ],
  exports: [],
  providers: [
    RegistrationService,
    AuthGuard,
    AppointmentService,
  ]
})
export class CoreModule {
}
