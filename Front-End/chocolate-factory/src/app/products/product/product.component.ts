import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Comment } from 'src/app/interfaces/Comment';
import { ProductDetails, ProductOrder } from 'src/app/interfaces/Product';
import { CartService } from 'src/app/services/cart.service';
import { environment } from 'src/environments/environment.prod';

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.css'],
})
export class ProductComponent implements OnInit {
  text: string = '';
  product: ProductDetails = {
    id: 0,
    name: '',
    description: '',
    imageUrl: '',
    price: 0,
    comments: [],
  };

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

  addToCart(product: ProductOrder): void {
    this.cart.addProduct(product);
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

  deleteComment(id: number): void {
    this.http
    .delete(
      `${environment.baseUrl}/comments/${id}`,
    )
    .subscribe(() => window.location.reload());
  }
}
