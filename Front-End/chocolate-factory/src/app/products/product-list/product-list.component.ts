import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Product } from 'src/app/interfaces/Product';
import { CartService } from 'src/app/services/cart.service';
import { environment } from 'src/environments/environment.prod';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css'],
})
export class ProductListComponent implements OnInit {
  products: Product[] = [];

  constructor(private http: HttpClient, private cart: CartService) {}

  ngOnInit(): void {
    this.http
      .get(`${environment.baseUrl}/products/all`)
      .subscribe((data: any) => {
        this.products = data;
      });
  }

  addToCart(id: number): void {
    this.cart.addProduct(id);
  }
}
