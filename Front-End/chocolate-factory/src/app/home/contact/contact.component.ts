import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { environment } from 'src/environments/environment.prod';

@Component({
  selector: 'app-contact',
  templateUrl: './contact.component.html',
  styleUrls: ['./contact.component.css'],
})
export class ContactComponent {
  title: string = '';
  contact: string = '';
  content: string = '';

  constructor(public http: HttpClient, public router: Router) {}

  sendMessage() {
    this.http
      .post(
        `${environment.baseUrl}/messages/add`,
        {
          title: this.title,
          contact: this.contact,
          content: this.content,
        },
        {
          headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
        }
      )
      .subscribe({
        next: (res) => {
          this.router.navigate(['home']);
          window.alert('Message successfully send!');
        },
        error: (err: Error) => window.alert(err.message),
      });
  }
}
