import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { User } from 'src/app/interfaces/User';
import { environment } from 'src/environments/environment.prod';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.css'],
})
export class ChangePasswordComponent {
  passwordMissMatch: boolean = false;
  error: string = '';
  id: number = 0;

  constructor(public http: HttpClient, public router: Router) {}

  ngOnInit(): void {
    const user: User = JSON.parse(localStorage.getItem('user')!);
    this.id = user.id;
  }

  submitForm(form: {
    oldPassword: string;
    newPassword: string;
    confirmPassword: string;
  }) {
    if (form.newPassword !== form.confirmPassword) {
      this.passwordMissMatch = true;
    }

    this.http
      .patch(
        `${environment.baseUrl}/users/${this.id}/password`,
        {
          oldPassword: form.oldPassword,
          newPassword: form.newPassword,
        },
        {
          headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
        }
      )
      .subscribe({
        next: () => {
          this.router.navigate(['user-profile']);
        },
        error: (err) => (this.error = err.error.message),
      });
  }
}
