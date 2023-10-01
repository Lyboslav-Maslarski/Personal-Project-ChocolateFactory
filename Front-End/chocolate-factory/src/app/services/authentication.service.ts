import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../interfaces/User';
import { environment } from '../../environments/environment.prod';
const API_URL = environment.baseUrl;

const httpOptions = {
  headers: new HttpHeaders({
    'Access-Control-Allow-Origin': '*',
    'Content-Type': 'application/json',
  }),
};

@Injectable({
  providedIn: 'root',
})
export class AuthenticationService {
  constructor(private http: HttpClient) {}

  login(username: string, password: string): Observable<User> {
    return this.http.post<User>(
      API_URL + 'auth/login',
      {
        username,
        password,
      },
      httpOptions
    );
  }

  register(
    username: string,
    email: string,
    password: string,
    confirmPassword: string
  ): Observable<any> {
    return this.http.post(
      API_URL + 'auth/register',
      {
        username,
        email,
        password,
        confirmPassword,
      },
      httpOptions
    );
  }

  findUserByUsername(username: string): Observable<User> {
    return this.http.get<User>(API_URL + `auth/find/${username}`, httpOptions);
  }

  updateRoles(id: number, roles: any[]): Observable<any> {
    return this.http.put(
      API_URL + 'auth/update_roles',
      {
        id,
        roles,
      },
      httpOptions
    );
  }
}
