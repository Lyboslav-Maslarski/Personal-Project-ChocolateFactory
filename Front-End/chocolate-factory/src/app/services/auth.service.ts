import { Injectable } from '@angular/core';
import {
  HttpClient,
  HttpErrorResponse,
  HttpHeaders,
} from '@angular/common/http';
import { Observable, catchError, map, throwError } from 'rxjs';
import { User } from '../interfaces/User';
import { environment } from '../../environments/environment.prod';
import { Router } from '@angular/router';

const API_URL = environment.baseUrl;
const headers = new HttpHeaders().set('content-type', 'application/json');

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  currentUser = {};

  constructor(private http: HttpClient, public router: Router) {}

  // Sign-up
  signUp(user: User) {
    if (this.getAuthToken() !== null) {
      headers.set('Authorization', 'Bearer ' + this.getAuthToken());
    }
    return this.http
      .post(`${API_URL}/users/register`, user, { headers: headers })
      .pipe(catchError(this.handleError))
      .subscribe({
        next: (res: any) => {
          window.localStorage.setItem('access_token', res.token);
          this.router.navigate(['login']);
        },
        error: (err) => {
          window.localStorage.removeItem('access_token');
          console.log(err);
        },
      });
  }
  // Sign-in
  signIn(email: string, password: string) {
    if (this.getAuthToken() !== null) {
      headers.set('Authorization', 'Bearer ' + this.getAuthToken());
    }
    return this.http
      .post<any>(
        `${API_URL}/users/login`,
        { email, password },
        { headers: headers }
      )
      .pipe(catchError(this.handleError))
      .subscribe({
        next: (res: any) => {
          window.localStorage.setItem('access_token', res.token);
          // this.getUserProfile(res._id).subscribe((res) => {
          //   this.currentUser = res;
          //   this.router.navigate(['user-profile/' + res.msg._id]);
          // });
        },
        error: (err) => {
          window.localStorage.removeItem('access_token');
          console.log(err);
        },
      });
  }

  getAuthToken(): string | null {
    return window.localStorage.getItem('access_token');
  }

  setAuthToken(token: string | null): void {
    if (token !== null) {
      window.localStorage.setItem('access_token', token);
    } else {
      window.localStorage.removeItem('access_token');
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
  // User profile
  getUserProfile(id: any): Observable<any> {
    let api = `${API_URL}/users/profile/${id}`;
    return this.http.get(api, { headers: headers }).pipe(
      map((res) => {
        return res || {};
      }),
      catchError(this.handleError)
    );
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
