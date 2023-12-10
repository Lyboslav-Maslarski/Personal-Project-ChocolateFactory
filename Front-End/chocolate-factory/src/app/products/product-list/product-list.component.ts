import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Product, ProductOrder } from 'src/app/interfaces/Product';
import { CartService } from 'src/app/services/cart.service';
import { environment } from 'src/environments/environment.prod';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css'],
})
export class ProductListComponent implements OnInit {
  products: Product[] = [];

  constructor(
    private http: HttpClient,
    private cart: CartService,
    private toastr: ToastrService
  ) {}

  ngOnInit(): void {
    this.http
      .get(`${environment.baseUrl}/products/all`)
      .subscribe((data: any) => {
        this.products = data;
      });
  }

  addToCart(product: ProductOrder): void {
    this.toastr.success('Product successfully added!');
    this.cart.addProduct(product);
  }
}
