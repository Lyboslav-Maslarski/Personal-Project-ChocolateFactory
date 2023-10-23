import { Component } from '@angular/core';

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

  constructor() {}

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

    console.log(secure_url);
    this.imageUrl = secure_url.toString();
  }

  addProduct() {}
}
