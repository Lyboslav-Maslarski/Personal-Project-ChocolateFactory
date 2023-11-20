import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { UserShort } from 'src/app/interfaces/User';
import { environment } from 'src/environments/environment.prod';

@Component({
  selector: 'app-admin-all-users',
  templateUrl: './admin-all-users.component.html',
  styleUrls: ['./admin-all-users.component.css'],
})
export class AdminAllUsersComponent implements OnInit {
  users: UserShort[] = [];

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.http.get(`${environment.baseUrl}/users/all`).subscribe((data: any) => {
      this.users = data;
    });
  }

  promoteUser(id: number) {
    this.http
      .patch(`${environment.baseUrl}/users/${id}/promote`, {})
      .subscribe(() => window.location.reload());
  }

  demoteUser(id: number) {
    this.http
      .patch(`${environment.baseUrl}/users/${id}/demote`, {})
      .subscribe(() => window.location.reload());
  }
}
