import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ProductDetails } from 'src/app/interfaces/Product';
import { environment } from 'src/environments/environment.prod';

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.css'],
})
export class ProductComponent implements OnInit {
  product: ProductDetails | undefined;
  constructor(
    private activatedRoute: ActivatedRoute,
    private http: HttpClient
  ) {}
  ngOnInit() {
    const id = this.activatedRoute.snapshot.params.id;
    console.log(id);
    
    this.http
      .get(`${environment.baseUrl}/products/${id}`)
      .subscribe((res: any) => (this.product = res));
  }
}
