import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Product } from 'src/app/interfaces/Product';
import { environment } from 'src/environments/environment.prod';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css'],
})
export class ProductListComponent implements OnInit {
  products: Product[] = [];
  constructor(private http: HttpClient) {}
  ngOnInit(): void {
    this.http
      .get(`${environment.baseUrl}/products/all`)
      .subscribe((data: any) => {
        console.log(this.products);
        this.products = data;
        console.log(this.products);
        
      });
  }
}
