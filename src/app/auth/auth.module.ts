import { NgModule } from '@angular/core';
import { AuthRoutingModule } from './auth-routing.module';
import { ForgotPasswordComponent } from './forgot-password/forgot-password.component';
import { LoginComponent } from './login/login.component';
import { RegistrationComponent } from './registration/registration.component';
import { SharedModule } from '../shared/shared.module';
import { AngularFireAuthModule } from 'angularfire2/auth';

@NgModule({
  declarations: [
    ForgotPasswordComponent,
    LoginComponent,
    RegistrationComponent,
  ],
  imports: [
    SharedModule,
    AngularFireAuthModule,
    AuthRoutingModule
  ]
})
export class AuthModule {
}
