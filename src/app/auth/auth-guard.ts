import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs';
import { MatSnackBar } from '@angular/material';
import { AngularFireAuth } from '@angular/fire/auth';
import { map } from 'rxjs/operators';

export class AuthGuard implements CanActivate {

  constructor(private afAuth: AngularFireAuth,
              private router: Router,
              private snackBar: MatSnackBar) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
    return this.afAuth.user.pipe(map(user => {
      if (user) {
        if (state.url.startsWith('/room/')) {
          this.router.navigate(['']);
        }
        return true;
      } else {
        this.router.navigate(['login']).then(() => {
          this.snackBar.open('Your session has expired.', 'OK');
        });
        return false;
      }
    }));
  }

}
