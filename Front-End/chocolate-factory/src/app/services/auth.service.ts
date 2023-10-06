import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { catchError, throwError } from 'rxjs';
import { environment } from '../../environments/environment.prod';
import { Router } from '@angular/router';
import { User, UserReq } from '../interfaces/User';

const API_URL = environment.baseUrl;

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  currentUser:User = {
    id: 0,
    token: '',
    email: '',
    fullName: '',
    city: '',
    address: '',
    phone: '',
    roles: []
  };

  constructor(private http: HttpClient, public router: Router) {}

  // Sign-up
  signUp(user: UserReq) {
    return this.http
      .post(`${API_URL}/users/register`, user)
      .pipe(catchError(this.handleError))
      .subscribe({
        next: (res: any) => {
          this.router.navigate(['login']);
        },
        error: (err) => {
          console.log(err);
        },
      });
  }
  // Sign-in
  signIn(email: string, password: string) {
    return this.http
      .post<any>(`${API_URL}/users/login`, { email, password })
      .pipe(catchError(this.handleError))
      .subscribe({
        next: (res: any) => {
          this.setAuthToken(res);
          this.router.navigate(['user-profile']);
        },
        error: (err) => {
          window.localStorage.removeItem('access_token');
          window.localStorage.removeItem('id');
          window.localStorage.removeItem('roles');
          console.log(err);
        },
      });
  }

  getAuthToken(): string | null {
    return window.localStorage.getItem('access_token');
  }

  setAuthToken(res: any): void {
    if (res.token !== null) {
      window.localStorage.setItem('access_token', res.token);
      window.localStorage.setItem('id', res.id);
      window.localStorage.setItem('roles', res.roles);
    } else {
      window.localStorage.removeItem('access_token');
      window.localStorage.removeItem('id');
      window.localStorage.removeItem('roles');
    }
  }

  get isLoggedIn(): boolean {
    let authToken = localStorage.getItem('access_token');
    return authToken !== null ? true : false;
  }

  doLogout() {
    let removeToken = localStorage.removeItem('access_token');
    if (removeToken == null) {
      this.router.navigate(['login']);
    }
  }

  // Error
  handleError(error: HttpErrorResponse) {
    let msg = '';
    if (error.error instanceof ErrorEvent) {
      // client-side error
      msg = error.error.message;
    } else {
      // server-side error
      msg = `Error Code: ${error.status}\nMessage: ${error.message}`;
    }
    window.alert(msg);
    return throwError(() => new Error(msg));
  }
}
