import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Message } from 'src/app/interfaces/Message';
import { environment } from 'src/environments/environment.prod';

@Component({
  selector: 'app-admin-all-messages',
  templateUrl: './admin-all-messages.component.html',
  styleUrls: ['./admin-all-messages.component.css'],
})
export class AdminAllMessagesComponent implements OnInit {
  messages: Message[] = [];

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.http
      .get(`${environment.baseUrl}/messages/all`)
      .subscribe((data: any) => {
        this.messages = data;
      });
  }

  setToAnswered(id: number) {
    this.http
      .patch(`${environment.baseUrl}/messages/${id}`, {})
      .subscribe(() => window.location.reload());
  }

  showContent(id: number) {
    let message: Message = this.messages.filter((m) => m.id === id)[0];
    if (message.showContent) {
      message.showContent = false;
    } else {
      message.showContent = true;
    }
  }
}
