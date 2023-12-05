import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { environment } from 'src/environments/environment.prod';

const CLOUD_NAME = 'lyb4ooo';

@Component({
  selector: 'app-add-product',
  templateUrl: './add-product.component.html',
  styleUrls: ['./add-product.component.css'],
})
export class AddProductComponent {
  error: string = '';
  success: boolean = false;
  imageUrl: string = '';

  constructor(public http: HttpClient, public router: Router) {}

  async uploadPhoto(event: any) {
    const file = event.target.files[0];
    const formData = new FormData();

    formData.append('file', file);
    formData.append('upload_preset', 'p4uprwkd');

    const { secure_url } = await fetch(
      'https://api.cloudinary.com/v1_1/' + CLOUD_NAME + '/image/upload',
      {
        method: 'POST',
        body: formData,
      }
    ).then((res) => res.json());

    this.imageUrl = secure_url.toString();
  }

  addProduct(form: {
    name: string;
    description: string;
    quantity: number;
    price: number;
  }) {
    this.http
      .post(
        `${environment.baseUrl}/products/add`,
        {
          name: form.name,
          description: form.description,
          imageUrl: this.imageUrl,
          quantity: form.quantity,
          price: form.price,
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
