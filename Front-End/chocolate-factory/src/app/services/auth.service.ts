import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment.prod';
import { Router } from '@angular/router';
import { UserRegister } from '../interfaces/User';

const API_URL = environment.baseUrl;

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  constructor(private http: HttpClient, public router: Router) {}

  // Register
  signUp(user: UserRegister) {
    return this.http.post(`${API_URL}/users/register`, user);
  }

  // Login
  signIn(email: string, password: string) {
    return this.http.post<any>(`${API_URL}/users/login`, { email, password });
  }

  getAuthToken(): string | null {
    return window.localStorage.getItem('access_token');
  }

  get isLoggedIn(): boolean {
    let authToken = localStorage.getItem('access_token');
    return authToken !== null ? true : false;
  }

  doLogout() {
    let removeToken = localStorage.removeItem('access_token');
    window.localStorage.removeItem('user');
    window.localStorage.removeItem('id');
    window.localStorage.removeItem('roles');
    if (removeToken == null) {
      this.router.navigate(['login']);
    }
  }
}
