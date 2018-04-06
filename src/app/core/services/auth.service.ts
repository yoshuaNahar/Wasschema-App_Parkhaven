import {Injectable} from '@angular/core';
import {RegistrationDetails} from '../models/RegistrationDetails';
import {AngularFireAuth} from 'angularfire2/auth';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../../environments/environment';

@Injectable()
export class RegistrationService {

  constructor(private afAuth: AngularFireAuth,
              private http: HttpClient) {
  }

  register(user: RegistrationDetails) {
    return this.http.post(`${environment.firebaseUrl}/registerUser`, user, {
      headers: {'Content-Type': 'application/json'}
    });
  }

}
