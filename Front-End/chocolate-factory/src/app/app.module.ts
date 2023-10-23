import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { CommonsModule } from './commons/commons.module';
import { HomeModule } from './home/home.module';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { UserModule } from './user/user.module';
import { AuthGuard } from './utils/AuthGuard';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { authInterceptorProviders } from '../app/utils/auth.interceptor';
import { ProductsModule } from './products/products.module';
import { OrderModule } from './order/order.module';
import { AdministrationModule } from './administration/administration.module';
import { ModeratorGuard } from './utils/ModeratorGuard';
import { AdminGuard } from './utils/AdminGuard';

@NgModule({
  declarations: [AppComponent],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    CommonsModule,
    HomeModule,
    NgbModule,
    UserModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    ProductsModule,
    OrderModule,
    AdministrationModule,
  ],
  providers: [authInterceptorProviders, AuthGuard, ModeratorGuard, AdminGuard],
  bootstrap: [AppComponent],
})
export class AppModule {}
