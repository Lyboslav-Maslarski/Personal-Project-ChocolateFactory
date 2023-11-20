import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Order } from 'src/app/interfaces/Order';
import { environment } from 'src/environments/environment.prod';

@Component({
  selector: 'app-admin-all-orders',
  templateUrl: './admin-all-orders.component.html',
  styleUrls: ['./admin-all-orders.component.css'],
})
export class AdminAllOrdersComponent implements OnInit {
  orders: Order[] = [];

  constructor(private http: HttpClient, public router: Router) {}

  ngOnInit(): void {
    this.http
      .get(`${environment.baseUrl}/orders/all`)
      .subscribe((data: any) => {
        this.orders = data;
      });
  }

  shipOrder(arg0: number) {
    throw new Error('Method not implemented.');
  }
  acceptOrder(arg0: number) {
    throw new Error('Method not implemented.');
  }
}
