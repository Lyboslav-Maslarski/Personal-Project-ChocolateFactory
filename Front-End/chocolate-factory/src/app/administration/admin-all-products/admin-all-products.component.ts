import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Product } from 'src/app/interfaces/Product';
import { environment } from 'src/environments/environment.prod';

@Component({
  selector: 'app-admin-all-products',
  templateUrl: './admin-all-products.component.html',
  styleUrls: ['./admin-all-products.component.css'],
})
export class AdminAllProductsComponent {
  products: Product[] = [];

  constructor(private http: HttpClient, public router: Router) {}

  ngOnInit(): void {
    this.http
      .get(`${environment.baseUrl}/products/all`)
      .subscribe((data: any) => {
        this.products = data;
      });
  }

  deleteProduct(productId: number) {
    this.http
      .delete(`${environment.baseUrl}/products/${productId}`)
      .subscribe((data: any) => {
        this.router.navigate(['products-all']);
      });
  }
}
