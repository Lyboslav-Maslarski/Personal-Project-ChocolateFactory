import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HTTP_INTERCEPTORS,
  HttpErrorResponse,
} from '@angular/common/http';
import { Observable, catchError } from 'rxjs';
import { AuthService } from '../services/auth.service';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  constructor(private authService: AuthService) {}

  intercept(
    request: HttpRequest<unknown>,
    next: HttpHandler
  ): Observable<HttpEvent<unknown>> {
    let authReq = request;
    const token = this.authService.getAuthToken();

    authReq = request.clone({
      headers: request.headers
        .set('Authorization', 'Bearer ' + token)
        .set('content-type', 'application/json'),
    });

    return next.handle(authReq).pipe(
      catchError((err) => {
        if (
          err instanceof HttpErrorResponse &&
          !request.url.includes('users/login') &&
          err.status === 403
        ) {
          this.authService.doLogout();
          return next.handle(authReq);
        }
        return next.handle(authReq);
      })
    );
  }
}

export const authInterceptorProviders = [
  { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },
];
