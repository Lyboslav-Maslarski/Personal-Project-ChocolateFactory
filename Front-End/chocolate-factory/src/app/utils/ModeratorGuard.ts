import { Injectable } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  CanActivate,
  Router,
  RouterStateSnapshot,
  UrlTree,
} from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from '../services/auth.service';

@Injectable()
export class ModeratorGuard implements CanActivate {
  constructor(public authService: AuthService, public router: Router) {}
  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ):
    | Observable<boolean | UrlTree>
    | Promise<boolean | UrlTree>
    | boolean
    | UrlTree {
    if (!this.authService.isLoggedIn) {
      window.alert('Access not allowed!aaaaaaa');
      this.router.navigate(['access-denied']);
      const roles = localStorage.getItem('roles');
      if (!roles?.includes('ROLE_MODERATOR')) {
        window.alert('Access not allowed!sssssss');
        this.router.navigate(['access-denied']);
      }
    }
    return true;
  }
}
