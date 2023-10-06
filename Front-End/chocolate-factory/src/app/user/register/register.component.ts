import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
})
export class RegisterComponent {
  email: string = '';
  password: string = '';
  confirmPassword: string = '';
  fullName: string = '';
  city: string = '';
  address: string = '';
  phone: string = '';
  constructor(public authService: AuthService) {}
  registerUser(): void {
    if(this.password!==this.confirmPassword){
      // TODO display error
    }
    this.authService.signUp({
      email: this.email,
      password: this.password,
      fullName: this.fullName,
      city: this.city,
      address: this.address,
      phone: this.phone,
    });
  }
}
