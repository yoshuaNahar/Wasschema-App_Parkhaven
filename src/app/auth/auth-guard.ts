import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot } from '@angular/router';
import { AuthService } from './auth.service';
import { Observable } from 'rxjs';
import { MatSnackBar } from '@angular/material';

export class AuthGuard implements CanActivate {

  constructor(private authService: AuthService,
              private router: Router,
              private snackBar: MatSnackBar) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
    if (this.authService.isUserLoggedIn()) {
      return true;
    } else {
      this.router.navigate(['login']).then(() => {
        this.snackBar.open('Your session has expired.', 'OK');
      });
      return false;
    }
  }

}
