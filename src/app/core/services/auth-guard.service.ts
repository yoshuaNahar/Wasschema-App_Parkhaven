import { ActivatedRouteSnapshot, CanActivate, RouterStateSnapshot } from '@angular/router';
import { RegistrationService } from './auth.service';
import { Observable } from 'rxjs';

export class AuthGuard implements CanActivate {

  constructor(private authService: RegistrationService) {

  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
    return true;
  }

}
