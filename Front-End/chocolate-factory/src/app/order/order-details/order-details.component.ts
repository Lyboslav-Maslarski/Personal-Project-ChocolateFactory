import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { OrderDetails } from 'src/app/interfaces/Order';
import { environment } from 'src/environments/environment.prod';

@Component({
  selector: 'app-order-details',
  templateUrl: './order-details.component.html',
  styleUrls: ['./order-details.component.css'],
})
export class OrderDetailsComponent implements OnInit {

  order: OrderDetails = {
    id: 0,
    orderNumber: '',
    total: 0,
    status: '',
    products: [],
  };

  constructor(
    private activatedRoute: ActivatedRoute,
    private http: HttpClient
  ) {}

  ngOnInit(): void {
    const orderNumber = this.activatedRoute.snapshot.params.orderNumber;

    this.http
      .get(`${environment.baseUrl}/orders/${orderNumber}`)
      .subscribe((res: any) => {
        this.order = res;
      });
  }
}
