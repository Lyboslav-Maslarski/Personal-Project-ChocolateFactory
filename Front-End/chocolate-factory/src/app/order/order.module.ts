import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { OrderDetailsComponent } from './order-details/order-details.component';
import { CommonsModule } from '../commons/commons.module';
import { RouterModule } from '@angular/router';



@NgModule({
  declarations: [
    OrderDetailsComponent
  ],
  imports: [
    CommonModule,
    CommonsModule,
    RouterModule
  ]
})
export class OrderModule { }
