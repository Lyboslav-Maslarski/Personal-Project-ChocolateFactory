import { Component } from '@angular/core';

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

  addProduct() {}
}
