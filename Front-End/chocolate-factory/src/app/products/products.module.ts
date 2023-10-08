import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProductListComponent } from './product-list/product-list.component';
import { ProductComponent } from './product/product.component';
import { CommonsModule } from '../commons/commons.module';
import { RouterModule } from '@angular/router';

@NgModule({
  declarations: [ProductListComponent, ProductComponent],
  imports: [CommonModule, CommonsModule, RouterModule],
})
export class ProductsModule {}
