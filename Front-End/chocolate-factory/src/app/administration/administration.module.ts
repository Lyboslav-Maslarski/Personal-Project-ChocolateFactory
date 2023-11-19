import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AddProductComponent } from './add-product/add-product.component';
import { AdminAllProductsComponent } from './admin-all-products/admin-all-products.component';
import { AdminAllOrdersComponent } from './admin-all-orders/admin-all-orders.component';
import { AdminAllUsersComponent } from './admin-all-users/admin-all-users.component';
import { CommonsModule } from '../commons/commons.module';
import { FormsModule } from '@angular/forms';
import { AdminAllMessagesComponent } from './admin-all-messages/admin-all-messages.component';
import { RouterModule } from '@angular/router';

@NgModule({
  declarations: [
    AddProductComponent,
    AdminAllProductsComponent,
    AdminAllOrdersComponent,
    AdminAllUsersComponent,
    AdminAllMessagesComponent,
  ],
  imports: [CommonModule, CommonsModule, FormsModule, RouterModule],
})
export class AdministrationModule {}
