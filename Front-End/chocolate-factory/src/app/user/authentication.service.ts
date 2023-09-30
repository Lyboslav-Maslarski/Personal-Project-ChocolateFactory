import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class AuthenticationService {
  users: any[] = [
    {
      id: 1,
      name: 'Example',
      email: 'example@example.com',
      password: '123',
    },
  ];
  constructor() {}
}
