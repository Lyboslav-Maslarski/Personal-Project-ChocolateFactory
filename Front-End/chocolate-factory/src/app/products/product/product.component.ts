import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Comment } from 'src/app/interfaces/Comment';
import { ProductDetails } from 'src/app/interfaces/Product';
import { CartService } from 'src/app/services/cart.service';
import { environment } from 'src/environments/environment.prod';

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.css'],
})
export class ProductComponent implements OnInit {
  text: string = '';
  product: ProductDetails | undefined;

  constructor(
    private activatedRoute: ActivatedRoute,
    private http: HttpClient,
    private cart: CartService
  ) {}

  ngOnInit() {
    const id = this.activatedRoute.snapshot.params.id;

    this.http
      .get(`${environment.baseUrl}/products/${id}`)
      .subscribe((res: any) => {
        this.product = res;
      });
  }

  addToCart(id: number | undefined): void {
    this.cart.addProduct(id);
  }

  submitComment(id: number | undefined): void {
    this.http
      .post<Comment>(
        `${environment.baseUrl}/comments/add`,
        {
          text: this.text,
          productId: id,
        },
        {
          headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
        }
      )
      .subscribe(() => window.location.reload());
  }
}
