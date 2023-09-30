import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AboutComponent } from './about/about.component';
import { HomeComponent } from './home/home.component';

@NgModule({
  declarations: [HomeComponent, AboutComponent],
  imports: [CommonModule],
})
export class HomeModule {}
