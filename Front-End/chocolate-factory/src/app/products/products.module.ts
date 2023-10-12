import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProductListComponent } from './product-list/product-list.component';
import { ProductComponent } from './product/product.component';
import { CommonsModule } from '../commons/commons.module';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';

@NgModule({
  declarations: [ProductListComponent, ProductComponent],
  imports: [CommonModule, CommonsModule, RouterModule, FormsModule],
})
export class ProductsModule {}
