import { Injectable } from '@angular/core';

const PRODUCTS = 'products';

@Injectable({
  providedIn: 'root',
})
export class CartService {
  products: number[] = [];

  constructor() {}

  public addProduct(product_id: number): void {
    this.products.push(product_id);
    window.sessionStorage.setItem(PRODUCTS, JSON.stringify(this.products));
  }

  public getProducts(): number[] {
    return window.sessionStorage.getItem(PRODUCTS)
      ? JSON.parse(window.sessionStorage.getItem(PRODUCTS)!)
      : [];
  }

  public initProducts(): void {
    window.sessionStorage.setItem(PRODUCTS, JSON.stringify(this.getProducts()));
  }

  public removeProduct(product_id: number): void {
    const index = this.products.indexOf(product_id);
    this.products.splice(index, 1);
    window.sessionStorage.setItem(PRODUCTS, JSON.stringify(this.products));
  }

  public removeAllProducts(): void {
    this.products = [];
    window.sessionStorage.setItem(PRODUCTS, JSON.stringify(this.products));
  }
}
