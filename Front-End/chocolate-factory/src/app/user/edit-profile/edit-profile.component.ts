import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from 'src/app/interfaces/User';
import { environment } from 'src/environments/environment.prod';

@Component({
  selector: 'app-edit-profile',
  templateUrl: './edit-profile.component.html',
  styleUrls: ['./edit-profile.component.css'],
})
export class EditProfileComponent implements OnInit {
  id: number = 0;
  email: string = '';
  fullName: string = '';
  city: string = '';
  address: string = '';
  phone: string = '';

  constructor(public http: HttpClient, public router: Router) {}
  
  ngOnInit(): void {
    const user: User = JSON.parse(localStorage.getItem('user')!);
    this.id = user.id;
    this.email = user.email;
    this.fullName = user.fullName;
    this.city = user.city;
    this.address = user.address;
    this.phone = user.phone;
  }

  submitForm() {
    this.http
      .patch(
        `${environment.baseUrl}/users/${this.id}`,
        {
          email: this.email,
          fullName: this.fullName,
          city: this.city,
          address: this.address,
          phone: this.phone,
        },
        {
          headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
        }
      )
      .subscribe({
        next: (res) => {
          window.localStorage.setItem('user', JSON.stringify(res));
          this.router.navigate(['user-profile']);
        },
        error: (err: Error) => window.alert(err.message),
      });
  }
}
