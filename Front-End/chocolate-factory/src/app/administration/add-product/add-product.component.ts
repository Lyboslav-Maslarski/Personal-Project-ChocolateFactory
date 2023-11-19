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
  name: string = '';
  description: string = '';
  imageUrl: string = '';
  quantity: number = 0;
  price: number = 0;

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

  addProduct() {
    this.http
      .post(
        `${environment.baseUrl}/products/add`,
        {
          name: this.name,
          description: this.description,
          imageUrl: this.imageUrl,
          quantity: this.quantity,
          price: this.price,
        },
        {
          headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
        }
      )
      .subscribe({
        next: (res) => {
          this.router.navigate(['products']);
        },
        error: (err: Error) => window.alert(err.message),
      });
  }
}
