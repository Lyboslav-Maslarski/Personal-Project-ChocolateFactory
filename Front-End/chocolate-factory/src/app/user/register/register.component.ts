import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
})
export class RegisterComponent {
  error = '';
  passwordMissMatch = false;

  constructor(public authService: AuthService, public router: Router) {}

  registerUser(form: {
    email: string;
    password: string;
    confirmPassword: string;
    fullName: string;
    city: string;
    address: string;
    phone: string;
  }): void {
    if (form.password !== form.confirmPassword) {
      this.passwordMissMatch = true;
      return;
    }
    this.authService
      .signUp({
        email: form.email,
        password: form.password,
        fullName: form.fullName,
        city: form.city,
        address: form.address,
        phone: form.phone,
      })
      .subscribe({
        next: () => {
          this.router.navigate(['login']);
        },
        error: (err) => {
          this.error = err.error.message;
        },
      });
  }
}
