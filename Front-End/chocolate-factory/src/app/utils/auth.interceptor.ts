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
import { Router } from '@angular/router';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  constructor(private authService: AuthService,private router: Router) {}

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
          err.status === 503
        ) {
          this.router.navigate(['/maintenance'])
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
