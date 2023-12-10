import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component } from '@angular/core';
import { environment } from 'src/environments/environment.prod';

@Component({
  selector: 'app-contact',
  templateUrl: './contact.component.html',
  styleUrls: ['./contact.component.css'],
})
export class ContactComponent {
  error: string = '';
  success: boolean = false;
  email: string = '';

  constructor(public http: HttpClient) {
    this.email = JSON.parse(localStorage.getItem('user')!).email;
  }

  sendMessage(form: { title: string; email: string; message: string }) {
    this.http
      .post(
        `${environment.baseUrl}/messages/add`,
        {
          title: form.title,
          contact: form.email,
          content: form.message,
        },
        {
          headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
        }
      )
      .subscribe({
        next: () => {
          this.success = true;
        },
        error: (err) => (this.error = err.error.message),
      });
  }
}
