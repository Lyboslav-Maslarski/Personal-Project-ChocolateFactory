import { CommonModule } from '@angular/common';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonsModule } from 'src/app/commons/commons.module';
import { ProductOrder } from 'src/app/interfaces/Product';
import { UserDetails } from 'src/app/interfaces/User';
import { AuthService } from 'src/app/services/auth.service';
import { CartService } from 'src/app/services/cart.service';
import { environment } from 'src/environments/environment.prod';
import { ToastrService } from 'ngx-toastr';

const API_URL = environment.baseUrl;

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css'],
  standalone: true,
  imports: [CommonModule, CommonsModule, RouterModule],
})
export class ProfileComponent implements OnInit {
  currentUser: UserDetails = {};
  products: ProductOrder[] = [];
  total: number = 0;

  constructor(
    private http: HttpClient,
    private cart: CartService,
    private auth: AuthService,
    private toastr: ToastrService
  ) {}

  ngOnInit(): void {
    this.http
      .get(`${API_URL}/users/` + window.localStorage.getItem('id'))
      .subscribe((user: UserDetails) => {
        this.currentUser = user;
      });

    this.products = this.cart.getProducts();
    this.total = this.products
      .map((p) => p.price)
      .reduce((acc, curr) => {
        acc += curr;
        return acc;
      }, 0);
  }

  removeItemFromCart(product: ProductOrder): void {
    this.toastr.success('Product successfully removed!');
    this.cart.removeProduct(product);
    window.location.reload();
  }

  clearCart(): void {
    this.toastr.success('Shopping cart cleared!');
    this.cart.removeAllProducts();
    window.location.reload();
  }

  makeOrder(): void {
    let productIds = this.products.map((p) => p.id);
    this.http
      .post(
        `${environment.baseUrl}/orders/add`,
        { products: productIds },
        {
          headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
        }
      )
      .subscribe(() => {
        this.toastr.success('Order successfully placed!');
        this.cart.removeAllProducts();
        window.location.reload();
      });
  }

  deleteProfile(id: number | undefined) {
    this.http.delete(`${environment.baseUrl}/users/` + id).subscribe((res) => {
      this.auth.doLogout();
    });
  }

  deleteOrder(id: number) {
    this.http.delete(`${environment.baseUrl}/orders/` + id).subscribe((res) => {
      window.location.reload();
    });
  }
}
