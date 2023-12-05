import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { CartService } from 'src/app/services/cart.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent {
  error = '';

  constructor(
    public authService: AuthService,
    private cart: CartService,
    public router: Router
  ) {}

  onSubmitLogin(value: { email: string; password: string }): void {
    this.authService.signIn(value.email, value.password).subscribe({
      next: (res: any) => {
        this.setAuthToken(res);
        this.cart.initProducts();
        this.router.navigate(['user-profile']);
      },
      error: (err) => {
        window.localStorage.removeItem('access_token');
        window.localStorage.removeItem('user');
        window.localStorage.removeItem('roles');
        this.error = err.error.message;
      },
    });
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
}
