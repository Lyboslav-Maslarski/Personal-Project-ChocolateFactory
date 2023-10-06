import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { CommonsModule } from 'src/app/commons/commons.module';
import { UserDetails } from 'src/app/interfaces/User';
import { environment } from 'src/environments/environment.prod';

const API_URL = environment.baseUrl;

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css'],
  standalone: true,
  imports: [CommonModule, CommonsModule],
})
export class ProfileComponent implements OnInit {
  currentUser: UserDetails = {};
  constructor(private http: HttpClient) {}
  ngOnInit(): void {
    this.http
      .get(`${API_URL}/users/` + window.localStorage.getItem('id'))
      .subscribe((user: UserDetails) => (this.currentUser = user));
  }
}
