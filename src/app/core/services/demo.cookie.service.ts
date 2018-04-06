// import { Injectable } from "@angular/core";
// import { HttpClient } from "@angular/common/http";
// import { environment } from '../../../environments/environment';
//
// @Injectable()
// export class DemoCookieService {
//
//   // https://angular.io/guide/router#milestone-5-route-guards
//   // This is a singleton service on a single user's pc. So it's okay to do this.
//   isLoggedIn = false;
//
//   constructor(private http: HttpClient) {
//   }
//
//   login(username: string, password: string, rememberMe: boolean) {
//     console.log(environment.serverUrl);
//     const loginObservable = this.http.get(`${environment.serverUrl}/user/info?remember-me=${rememberMe}`, {
//       headers: {'Authorization': 'Basic ' + btoa(username + ':' + password)},
//       withCredentials: true
//     });
//
//     loginObservable.subscribe(
//       content => this.isLoggedIn = true,
//       error => console.log('false login'));
//
//     return loginObservable;
//   }
//
//   logout() {
//     this.isLoggedIn = false;
//   }
//
//   getAuthenticatedUser() {
//     return this.http.get(`${environment.serverUrl}/user`, {
//       headers: {'X-Requested-With': 'XMLHttpRequest'},
//       // used so that you don't receive a WWW-Authenticate header
//       withCredentials: true
//     });
//   }
//
//   forgotPassword(username: string) {
//     return this.http.post(`${environment.serverUrl}/user/forgotPassword`, {username});
//   }
//
//   resetPassword(password: string, matchingPassword: string) {
//     return this.http.post(`${environment.serverUrl}/user/resetPassword`, {
//       password,
//       matchingPassword
//     }, {
//       withCredentials: true
//     });
//   }
//
// }
