import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ProductUpdate } from 'src/app/interfaces/Product';
import { environment } from 'src/environments/environment.prod';

@Component({
  selector: 'app-update-product',
  templateUrl: './update-product.component.html',
  styleUrls: ['./update-product.component.css'],
})
export class UpdateProductComponent implements OnInit {
  error: string = '';
  success: boolean = false;
  id = this.activatedRoute.snapshot.params.id;
  name: string = '';
  description: string = '';
  quantity: number = 0;
  price: number = 0;

  constructor(
    private activatedRoute: ActivatedRoute,
    public http: HttpClient,
    public router: Router
  ) {}

  ngOnInit(): void {
    this.http
      .get(`${environment.baseUrl}/products/update/${this.id}`)
      .subscribe({
        next: (product: any) => {
          this.name = product.name;
          this.description = product.description;
          this.quantity = product.quantity;
          this.price = product.price;
        },
        error: (err: Error) => window.alert(err.message),
      });
  }

  updateProduct(form: {
    name: string;
    description: string;
    quantity: number;
    price: number;
  }) {
    this.http
      .patch(
        `${environment.baseUrl}/products/${this.id}`,
        {
          name: form.name,
          description: form.description,
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
