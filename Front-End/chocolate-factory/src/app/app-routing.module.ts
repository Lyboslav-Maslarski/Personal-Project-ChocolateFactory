import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RegisterComponent } from './user/register/register.component';
import { LoginComponent } from './user/login/login.component';
import { ProfileComponent } from './user/profile/profile.component';
import { AuthGuard } from './utils/AuthGuard';
import { HomeComponent } from './home/home/home.component';
import { AboutComponent } from './home/about/about.component';
import { ProductListComponent } from './products/product-list/product-list.component';
import { ProductComponent } from './products/product/product.component';
import { EditProfileComponent } from './user/edit-profile/edit-profile.component';
import { ChangePasswordComponent } from './user/change-password/change-password.component';
import { OrderDetailsComponent } from './order/order-details/order-details.component';
import { AddProductComponent } from './administration/add-product/add-product.component';
import { ModeratorGuard } from './utils/ModeratorGuard';
import { AdminAllProductsComponent } from './administration/admin-all-products/admin-all-products.component';
import { AdminAllOrdersComponent } from './administration/admin-all-orders/admin-all-orders.component';
import { AdminGuard } from './utils/AdminGuard';
import { AdminAllUsersComponent } from './administration/admin-all-users/admin-all-users.component';
import { AdminAllMessagesComponent } from './administration/admin-all-messages/admin-all-messages.component';

const routes: Routes = [
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  { path: 'home', component: HomeComponent },
  { path: 'about', component: AboutComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  {
    path: 'user-profile',
    component: ProfileComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'user-edit-profile',
    component: EditProfileComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'user-edit-password',
    component: ChangePasswordComponent,
    canActivate: [AuthGuard],
  },
  { path: 'products', component: ProductListComponent },
  { path: 'products/:id', component: ProductComponent },
  { path: 'order-details/:orderNumber', component: OrderDetailsComponent },
  {
    path: 'product-add',
    component: AddProductComponent,
    canActivate: [AuthGuard, ModeratorGuard],
  },
  {
    path: 'products-all',
    component: AdminAllProductsComponent,
    canActivate: [AuthGuard, ModeratorGuard],
  },
  {
    path: 'orders-all',
    component: AdminAllOrdersComponent,
    canActivate: [AuthGuard, ModeratorGuard],
  },
  {
    path: 'messages-all',
    component: AdminAllMessagesComponent,
    canActivate: [AuthGuard, ModeratorGuard],
  },
  {
    path: 'users-all',
    component: AdminAllUsersComponent,
    canActivate: [AuthGuard, AdminGuard],
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
