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
import { UpdateProductComponent } from './administration/update-product/update-product.component';
import { ContactComponent } from './home/contact/contact.component';
import { PageNotFoundComponent } from './commons/page-not-found/page-not-found.component';
import { AccessDeniedComponent } from './commons/access-denied/access-denied.component';
import { LoginRequiredComponent } from './commons/login-required/login-required.component';
import { MaintenanceComponent } from './home/maintenance/maintenance.component';

const routes: Routes = [
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  { path: 'maintenance', component: MaintenanceComponent },
  { path: 'home', component: HomeComponent },
  { path: 'about', component: AboutComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'contact', component: ContactComponent, canActivate: [AuthGuard] },
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
  {
    path: 'order-details/:orderNumber',
    component: OrderDetailsComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'product-update/:id',
    component: UpdateProductComponent,
    canActivate: [ModeratorGuard],
  },
  {
    path: 'product-add',
    component: AddProductComponent,
    canActivate: [ModeratorGuard],
  },
  {
    path: 'products-all',
    component: AdminAllProductsComponent,
    canActivate: [ModeratorGuard],
  },
  {
    path: 'orders-all',
    component: AdminAllOrdersComponent,
    canActivate: [ModeratorGuard],
  },
  {
    path: 'messages-all',
    component: AdminAllMessagesComponent,
    canActivate: [ModeratorGuard],
  },
  {
    path: 'users-all',
    component: AdminAllUsersComponent,
    canActivate: [AdminGuard],
  },
  { path: 'access-denied', component: AccessDeniedComponent },
  { path: 'login-required', component: LoginRequiredComponent },
  { path: '**', component: PageNotFoundComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { onSameUrlNavigation: 'reload' })],
  exports: [RouterModule],
  providers: [AuthGuard, ModeratorGuard, AdminGuard],
})
export class AppRoutingModule {}
