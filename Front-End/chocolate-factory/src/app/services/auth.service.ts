import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { environment } from '../../environments/environment.prod';
import { Router } from '@angular/router';
import { User, UserRegister } from '../interfaces/User';
import { CartService } from './cart.service';

const API_URL = environment.baseUrl;

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  currentUser: User = {
    id: 0,
    token: '',
    email: '',
    fullName: '',
    city: '',
    address: '',
    phone: '',
    roles: [],
  };

  constructor(
    private http: HttpClient,
    public router: Router,
    private cart: CartService
  ) {}

  // Register
  signUp(user: UserRegister) {
    return this.http.post(`${API_URL}/users/register`, user).subscribe({
      next: (res: any) => {
        this.router.navigate(['login']);
      },
      error: (err) => {
        window.alert(err.error.message);
      },
    });
  }

  // Login
  signIn(email: string, password: string) {
    return this.http
      .post<any>(`${API_URL}/users/login`, { email, password })
      .subscribe({
        next: (res: any) => {
          this.setAuthToken(res);
          this.cart.initProducts();
          this.router.navigate(['user-profile']);
        },
        error: (err) => {
          window.localStorage.removeItem('access_token');
          window.localStorage.removeItem('user');
          window.localStorage.removeItem('roles');
          window.alert(err.error.message);
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
      window.localStorage.setItem('user', JSON.stringify(res));
      window.localStorage.setItem('roles', res.roles);
    } else {
      window.localStorage.removeItem('access_token');
      window.localStorage.removeItem('user');
      window.localStorage.removeItem('roles');
    }
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
