import { Injectable } from '@angular/core';
import { ProductOrder } from '../interfaces/Product';

const PRODUCTS = 'products';

@Injectable({
  providedIn: 'root',
})
export class CartService {
  products: ProductOrder[] = [];

  constructor() {}

  public addProduct(product: ProductOrder): void {
    this.products = this.getProducts();
    this.products.push(product);
    window.sessionStorage.setItem(PRODUCTS, JSON.stringify(this.products));
  }

  public getProducts(): [] {
    return window.sessionStorage.getItem(PRODUCTS)
      ? JSON.parse(window.sessionStorage.getItem(PRODUCTS)!)
      : [];
  }

  public initProducts(): void {
    window.sessionStorage.setItem(PRODUCTS, JSON.stringify(this.products));
  }

  public removeProduct(product: ProductOrder): void {
    this.products = this.getProducts();
    const index = this.products.findIndex((p) => p.id === product.id);
    this.products.splice(index, 1);
    window.sessionStorage.setItem(PRODUCTS, JSON.stringify(this.products));
  }

  public removeAllProducts(): void {
    this.products = [];
    window.sessionStorage.setItem(PRODUCTS, JSON.stringify(this.products));
  }
}
