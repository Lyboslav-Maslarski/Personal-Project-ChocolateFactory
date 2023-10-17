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
  id: number = 0;
  oldPassword: string = '';
  newPassword: string = '';
  confirmPassword: string = '';

  constructor(public http: HttpClient, public router: Router) {}

  ngOnInit(): void {
    const user: User = JSON.parse(localStorage.getItem('user')!);
    this.id = user.id;
  }

  submitForm() {
    if (this.newPassword !== this.confirmPassword) {
      window.alert("password don't match!");
    }

    this.http
      .patch(
        `${environment.baseUrl}/users/${this.id}/password`,
        {
          oldPassword: this.oldPassword,
          newPassword: this.newPassword,
        },
        {
          headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
        }
      )
      .subscribe({
        next: (res) => {
          this.router.navigate(['user-profile']);
        },
        error: (err: Error) => window.alert(err.message),
      });
  }
}
